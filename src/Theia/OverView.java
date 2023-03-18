package Theia;

import Apollo.Apollo;
import Gaia.Block;
import Apollo.Point;
import Apollo.Map;
import Simulation.Simulation;

import javax.swing.*;
import java.awt.*;

/**
 * Visual component that represents the map. Even thought it displays only a single attribute a time,
 * it is possible to change the attribute displayed. The value of the attirbute is displyed in color. The
 * greater the value, the more red. The lesser the value, the more blue.
 */
public class OverView extends JPanel{
    private static final Rectangle rectangle = new Rectangle(5, 240, 0, 0);
    private int cellSize = 9;
    private JLabel[][] grid;

    public int getCellSize() {
        return cellSize;
    }

    /**
     * Creates a new overview component.
     * @param map matrix with the value of the specified attribute for every block
     */
    public OverView(int[][] map) {
        setLayout(null);

        int height = map.length;
        int width = map[0].length;

        grid = new JLabel[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                createButton(new Point(j, i), Theia.getColor(map[i][j]));
                if (i == height-1 && j == width-1) {
                    Rectangle rect = getCellRect(new Point(j, i), true);
                    rectangle.width = rect.x + cellSize;
                    rectangle.height = rect.y + cellSize;
                }
            }
        }
        setBounds(rectangle);
    }

    // TODO: 2022-12-04 merge this function with Theia.isSituated and call it isEnclosed
    public boolean isInside(Point point) {
        System.out.println(point);
        boolean inX = 0 <= point.x && point.x < grid[0].length;
        boolean inY = 0 <= point.y && point.y < grid.length;
        return inX && inY;
    }

    public Color getAttribute(Point point) {
        return grid[point.y][point.x].getBackground();
    }

    /**
     * Creates new tile representing a block on the visual map.
     * @param point position of the block
     * @param color color that should be displayed
     */
    private void createButton(Point point, Color color) {
        JLabel button = new JLabel();
        button.setBounds(getCellRect(point, true));
        button.setName("Cell(" + point.x + "," + point.y + ")");
        button.setBorder(BorderFactory.createLineBorder(new Color(124, 126, 128)));
        button.setBackground(color);
        button.setOpaque(true);
        add(button);
        grid[point.y][point.x] = button;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    // return the label of the specified index
    public JLabel getCell(Point position) {
        return grid[position.y][position.x];
    }

    /**
     * Convert screen coordinates to map coordinates
     * @param point screen coordinates
     * @return map coordinates
     */
    public Point findCell(Point point) {
        int i = (point.x - rectangle.x) / 8;
        int j = (point.y - rectangle.y - 30) / 8;
        point = new Point(i, j);
        return point;
    }

    /**
     * Find the rectangle of a specific tile
     * @param point map coordinate
     * @param relative if the origin is the top left block of the overview map
     * @return rectangle occupied on the screen by the tile
     */
    public Rectangle getCellRect(Point point, boolean relative) {
        return new Rectangle((relative ? 0 : rectangle.x) + (cellSize -1) * point.x,
                (relative ? 0 : rectangle.y) + (cellSize -1) * point.y, cellSize, cellSize);
    }

    /**
     * Alterate a tile on the overview map.
     * @param point map coordinate of the block to modify
     * @param color color that should be displayed
     */
    public void modify(Point point, Color color) {
        JLabel label = grid[point.y][point.x];
        label.setBackground(color);
        repaint(getCellRect(point, false));
        revalidate();
    }

//    @Deprecated
//    private int collect(Map map, Point center) {
//        Block[] places = map.rotate(center, 1);
//        int[] nearbyValues = new int[places.length];
//        for (int i = 0; i < places.length; i++) {
//            Block block = places[i];
//            // TODO: 2022-12-14 change this
////            nearbyValues[i] = block.mineral;
//        }
//        return Apollo.average(nearbyValues);
//    }
//    @Deprecated
//    private void test(Map map) {
//        int width = map.width/3;
//        int height = map.height/3;
//
//        setLayout(new GridLayout(height, width));
//        setBounds(rectangle);
//        for (int i = 0; i < height; i++) {
//            for (int j = 0; j < width; j++) {
//                Point center = new Point(3*j+1, 3*i+1);
//                JButton button = new JButton();
//                button.setName(center.toString());
//                button.setBorder(BorderFactory.createLineBorder(Color.black));
//                button.addActionListener(e -> System.out.println("Button pressed -> " + ((JButton) e.getSource()).getName()));
//                button.setBackground(Theia.getColor(collect(map, center)));
//                button.setBorderPainted(false);
//                button.setOpaque(true);
//                button.setPreferredSize(new Dimension(22, 22));
//                add(button);
//            }
//        }
//    }
//
}
