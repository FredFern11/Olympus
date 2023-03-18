//package Theia;
//
//import Gaia.Block;
//import Apollo.Point;
//import Apollo.Map;
//
//import javax.swing.*;
//import java.awt.*;
//import java.util.Arrays;
//import java.util.HashMap;
//
//public class Controller extends JPanel {
//    private final Rectangle rectangle = new Rectangle(2, -2, 130, 130);
//
//    public Rectangle getRectangle() {
//        return rectangle;
//    }
//
//    public Controller(Point center, Map map, HashMap<Point, Integer> polarConvert) {
//        int width = Theia.divisor;
//        setBounds(rectangle);
//        // will store the grid of buttons
//        Container container = new Container();
//        container.setLayout(new GridLayout(width, width, 0, 0));
//        // calculate the amount of block right of the center
//        int radius = (width-1)/2;
//        // get all the nearby blocks in the radius
//        Block[] nearby = map.rotate(center, radius);
//        // because polar coordinate do not work in cartesian, this array is the intermediate
//        JButton[] order = new JButton[width*width];
//        // loop on every block to generate a button
//        for (int i = 0; i < nearby.length; i++) {
//            JButton button = new JButton();
//            button.setBorderPainted(false);
//            button.setOpaque(true);
//            // find the background color using the gradient and the value of the field
//            button.setBackground(Theia.getColor(nearby[i].mineral));
//            button.setPreferredSize(new Dimension(43, 43));
//            // because polarConvert uses block between -1 and 1, we need to find the change in position
//            int index = polarConvert.get(nearby[i].position.offset(center).scale(-1));
//            // insert the button in the ordered array
//            order[index] = button;
//        }
//        // add the button into the container following a linear fashion
//        Arrays.stream(order).forEach(n -> container.add(n));
//        add(container);
//    }
//}
