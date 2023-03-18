package Theia;

import Apollo.Apollo;
import Apollo.Point;
import Gaia.Block;
import Gaia.*;
import Hermes.Channel;
import Hermes.Reply;
import Hermes.Request;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Queue;

public class Theia extends JFrame {
    public Channel channel;
    public int width, height;

    // color gradient from blue (0) to red (100)
    private static Color[] gradient = createGradient(Color.blue, Color.red, 101);

    private OverView overView;
    private Description description;

    // last position clicked by mouse
    private volatile Point cursor;
    private Color prevColor;

    // converts a polor coordinate to an index on an array
    private HashMap<Point, Integer> polarConvert = new HashMap<>();
    public static final int divisor = 3;

    /**
     * Ask a subordinate function for the attributes of a block. Then it moves the cursor and change the cursor
     * to the next position
     * @param next next position of the cursor
     */
    public void update(Point next) {
        Request request = channel.generate(Gaia.class)
                .add(0, "getBlock", next);
        Reply reply = channel.send(request);
        Block block = (Block) reply.getResult(0).getAsset(0);
        moveCursor(block.position);
        actualize(block);
    }


    public Theia(int width, int height) {
        super("GAIA 1");
        this.channel = new Channel(this);
        this.width = width;
        this.height = height;

        setResizable(false);
        setPolarConvert(divisor);
        addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent event) {
                Point position = new Point(event.getX(), event.getY());
                if (isSituated(position, overView.getRectangle())) {
                    update(overView.findCell(position));
                }
            }
            @Override
            public void mouseClicked(MouseEvent event) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });

        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                Point delta = convert(e);
                if (delta != null && cursor != null) {
                    Point next = cursor.clone().offset(delta.scale(e.isShiftDown() ? 4 : 1));
                    if (overView.isInside(next)) {
                        update(next);
                    }
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        overView = new OverView(getStaticMap("mineral"));
        add(overView);

        description = new Description(new Soil(new Point(0,0)));
        add(description);
        run();
    }

    /**
     * Converts a key event to a displacement
     * @param event key pressed
     * @return displacement
     */
    private Point convert(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.VK_LEFT: return new Point(-1, 0);
            case KeyEvent.VK_RIGHT: return new Point(1, 0);
            case KeyEvent.VK_UP: return new Point(0, -1);
            case KeyEvent.VK_DOWN: return new Point(0, 1);

            case KeyEvent.VK_Q: return new Point(-1, -1);
            case KeyEvent.VK_W: return new Point(0, -1);
            case KeyEvent.VK_E: return new Point(1, -1);
            case KeyEvent.VK_D: return new Point(1, 0);
            case KeyEvent.VK_C: return new Point(1, 1);
            case KeyEvent.VK_X: return new Point(0, 1);
            case KeyEvent.VK_Z: return new Point(-1, 1);
            case KeyEvent.VK_A: return new Point(-1, 0);
        }
        return null;
    }

    /**
     * Funtion that run continously to find changes in the attributes of the block chosen so that it can change
     * the description or modification to blocks on the map so that it changes the color displayed.
     */
    public void run() {
        while (true) {
            Point memCursor = cursor;
            Request request = channel.generate(Gaia.class)
                    .add(0, "getChanges");

            if (cursor != null) {
                request.add(0, "getBlock", cursor);
            }

            if (request.getSize() > 0) {
                Reply reply = channel.send(request);
                Queue<Block> changes = (Queue<Block>) reply.getResult(0).getAsset(0);
                if (request.getProtocol(0).getSize() == 2 && memCursor.equals(cursor)) {
                    Block chosen = (Block) reply.getResult(0).getAsset(1);
                    actualize(chosen);
                }

                while (!changes.isEmpty()) {
                    Block block = changes.poll();
                    if (!block.position.equals(cursor)) {
                        // TODO: 2022-12-14 change this function
//                        overView.modify(block.position, getColor(block.mineral));
                    }
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get a snapshot of the map of a certain subordiante function on a specific attribute
     * @param attribute name of the field
     * @return matrix containing the value of a specific attribute for every block
     */
    private int[][] getStaticMap(String attribute) {
        Request request = channel.generate(Gaia.class);
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                request.add(0, "getBlock", new Point(j, i));
            }
        }
        Reply reply = channel.send(request);
        return extract(reply, attribute);
    }

    /**
     * Extract a specific attribute from all the blocks in the map.
     * @param reply contains all the blocks to extarct the data from
     * @param attribute name of the field
     * @return matrix containing the value of every block for a specific attribute
     */
    private int[][] extract(Reply reply, String attribute) {
        int[][] map = new int[this.height][this.width];
        int lenght = reply.getResult(0).getLenght();
        for (int i = 0; i < lenght; i++) {
            Block block = (Block) reply.getResult(0).getAsset(i);
            map[i / this.width][i % this.width] = block.get(attribute);
        }
        return map;
    }

    // return the color associated with the value [1,100]
    public static Color getColor(int value) {
        return gradient[value];
    }

    /**
     * Adds a component to the overview map by removing the previous instance.
     * @param comp the component to be added
     * @return component to be added
     */
    @Override
    public Component add(Component comp) {
        super.add(comp);
        repaint();
        revalidate();
        setVisible(true);
        return comp;
    }

    /**
     * Moves the cursor to a different position
     * @param next next position
     */
    public void moveCursor(Point next) {
        if (cursor != null) overView.modify(cursor, prevColor);
        cursor = next;
        prevColor = overView.getAttribute(next);
        overView.modify(next, Color.white);
    }

    /**
     * Creates a new description for a specified block.
     * @param block specic block to be described
     */
    private void actualize(Block block) {
        if (description.wasAltered(block)) {
            remove(description);
            description = new Description(block);
            add(description);
        }
    }

    /**
     * Check if a point lies inside a rectangle
     * @param position
     * @param rectangle
     * @return if the point lies inside the rectangle
     */
    private boolean isSituated(Point position, Rectangle rectangle) {
        boolean onXAxis = rectangle.x <= position.x && position.x <= rectangle.x + rectangle.width;
        boolean onYAxis = rectangle.y <= position.y && position.y <= rectangle.y + rectangle.height;
        return onXAxis && onYAxis;
    }

    /**
     * Generate a gradient between two colors using the linear interpolation method.
     * @param color1 color that represents a value of 0
     * @param color2 color that represnets a value of 100
     * @param numSteps number of intermediate colors
     * @return color gradient between the two colors
     */
    private static Color[] createGradient(final Color color1, final Color color2, int numSteps) {
        Color[] gradient = new Color[numSteps];
        double iNorm;
        for (int i = 0; i < numSteps; i++) {
            iNorm = i / (double) numSteps; // i is element of [0, 1]
            int newRed = (int) Apollo.lerp(color1.getRed(), color2.getRed(), iNorm);
            int newGreen = (int) Apollo.lerp(color1.getGreen(), color2.getGreen(), iNorm);
            int newBlue = (int) Apollo.lerp(color1.getBlue(), color2.getBlue(), iNorm);
            int newAlpha = (int) Apollo.lerp(color1.getAlpha(), color2.getAlpha(), iNorm);
            gradient[i] = new Color(newRed, newGreen, newBlue, newAlpha);
        }
        return gradient;
    }

    @Deprecated
    private void setPolarConvert(int width) {
        Point point = new Point(-1 * ((width -1)/2), 1 * ((width -1)/2));
        Point end = new Point(-point.x, -point.y);
        int index = 0;
        while (!point.equals(end)) {
            polarConvert.put(point.clone(), index++);
            if (point.x == (width - 1)/2) {point.offset(new Point(-2, -1));}
            else {point.x++;}
        }
        polarConvert.put(end.clone(), index);
    }
}
