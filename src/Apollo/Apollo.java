package Apollo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Subordinate function used for knowledge purposes.
 */
public class Apollo {
    /**
     * Linear interpolation method to find a continuity of values between 2 end points
     * @param A first endpoint
     * @param B second endpoint
     * @param t interval
     * @return value between endpoints at certain point
     */
    public static double lerp(double A, double B, double t) {return (A+(B-A)*t);}

    /**
     * Average of the elements in the array
     * @param array list of numbers
     * @return average
     */
    public static int average(int[] array) {
        int sum = 0;
        for (int j : array) sum += j;
        return (int) round((double) sum / array.length, 0);
    }

    /**
     * Rounds a number to a decimal place
     * @param number
     * @param position the amount of decimal digits
     * @return the rounded number
     */
    public static double round(double number, int position) {
        // example: 318.91821 rounded to the 3rd decimal place (digit 8)
        // important digit will be the digit after the wanted position (digit 2)
        ++position;
        // make an integer by pushing the digit to the left such that the last digit is the digit after the position (3 189 182)
        int whole = (int) (number * Math.pow(10, position));
        // extract the important digit 2
        int unit = whole % 10;
        // apply the rule to round
        if (unit < 5) {whole -= unit;}
        else {whole += (10 - unit);}
        // push the digits with the same number of digit but in the opposite direction, right (3 189 182 -> 318.918)
        return whole / Math.pow(10, position);
    }

    /**
     * Find a specific field in a class using reflection
     * @param name Identifier of the field specified in the class
     * @param object Class of the object targeted
     * @return specified field
     * @throws RuntimeException if the field isn't found
     */
    public static Field findField(String name, Class<?> object) {
        Field[] fieldArray = object.getDeclaredFields();
        int i = 0;
        while (!fieldArray[i++].getName().equals(name)) {
            if (i == fieldArray.length)
                throw new RuntimeException("field " + name + " not found in class " + object.getSimpleName());
        }
        return fieldArray[--i];
    }

    /**
     * Find a specific method in a class using reflection
     * @param name Identifier of the field specified in the class
     * @param object Class of the object targeted
     * @return specified method
     * @throws RuntimeException if the method isn't found
     */
    public static Method findMethod(String name, Class<?> object) {
        Method[] methodArray = object.getDeclaredMethods();
        int i = 0;
        while (!methodArray[i++].getName().equals(name)) {
            if (i == methodArray.length)
                throw new RuntimeException("Method " + name + " not found in class " + object.getSimpleName());
        }
        return methodArray[--i];
    }
}
