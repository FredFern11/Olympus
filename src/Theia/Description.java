package Theia;

import Gaia.Block;

import javax.swing.*;
import java.awt.*;

/**
 * Use to describe a specific block with all of its attributes in a panel.
 */
public class Description extends JPanel{
    private final Rectangle rectangle = new Rectangle(1210, 200, 225, 700);
    private final Block block;

    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * Create a JPanel with the block's information.
     * @param block block on which we extract the data
     */
    public Description(Block block) {
        super();
        this.block = block;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBounds(rectangle);
        setBackground(new Color(238, 238, 238));
        add(build(block));
    }

    private JPanel build(Object object) {
        String[] attributes;
        int[] values;

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        if (object.getClass() == Block.class) {
            Block block = (Block) object;
            panel.setBorder(BorderFactory.createTitledBorder("LITHOSPHERE"));
            attributes = new String[]{"Contamination", "Stability", "Mineral", "Humidity", "Temperature"};
            // TODO: 2022-12-14 change this 
            values = new int[1];
//            values = block.extract();
        } else {
            attributes = null;
            values = null;
        }
        for (int i = 0; i < attributes.length; i++) {
            JLabel label = new JLabel(attributes[i] + ": " + values[i]);
            label.setBounds(rectangle.x, rectangle.y + 5 * i, rectangle.width/4, rectangle.height);
            panel.add(label);

            JProgressBar progressBar = new JProgressBar(0, 100);
            progressBar.setOpaque(true);
            progressBar.setValue(values[i]);

            panel.add(progressBar);
            panel.add(Box.createVerticalStrut(8));
        }
        return panel;
    }

    /**
     * Check if this description is about the same block
     * @param block other block
     * @return whether this description is about this block
     */
    public boolean wasAltered(Block block) {
        return !this.block.equals(block);
    }
}
