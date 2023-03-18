package Test;

import Simulation.Date;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
//        Date date = new Date(525600);
//        System.out.println(date);
//        long a = System.currentTimeMillis();
        String[] array = new String[366];
        for (int i = 0; i < 525600+1; i+=60*24) {
            Date date = new Date(i + 3 * 525600);
            System.out.println(i + "->" + date);
            array[i/(60*24)] = date.toString();
        }
        for (int i = 0; i < 12; i++) {
            int finalI = i;
            System.out.print(Arrays.stream(array).filter(n -> n.contains(Date.monthName[finalI])).count() + " ");
        }
//        Date date = new Date(1575300);
//        System.out.println(date);
//        System.out.println(System.currentTimeMillis() - a);
    }
}
