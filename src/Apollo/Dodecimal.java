package Apollo;

import java.util.Arrays;

/**
 * Alternative number system based on base 12. It supports basic arithmatics.
 */
public class Dodecimal {
    private final int base = 12;
    private int[] glyph;
    private final char[] digits = "012345ABCDEF".toCharArray();

    /**
     * Converts a number in base 10 to a number in the specified base
     * @param number base 10
     */
    public Dodecimal(int number) {
        if (number == 0 || number == 1) {
            this.glyph = new int[]{number};
            return;
        }
        double n = (Math.log(number) / Math.log(base));
        glyph = new int[(int) (((n * 10) % 10 == 0 ? n : n + 1))];

        int remainder = number;
        for (int i = glyph.length-1; i >= 0; i--) {
            int index = (int) (remainder/Math.pow(base, i));
            glyph[glyph.length-1-i] = index;
            remainder -= Math.pow(base, i) * index;
        }
    }

    /**
     * Creates a dodecimal object from its string
     * @param glyph a string of digits or letters
     */
    public Dodecimal(String glyph) {
        this.glyph = new int[glyph.length()];
        char[] split = glyph.toCharArray();
        for (int i = 0; i < split.length; i++) {
            this.glyph[i] = indexOf(split[i]);
        }
    }

    /**
     * Creates a dodecimal object from the value of each of its digit
     * @param data contains the value of each digit in the number
     */
    public Dodecimal(int[] data) {
        this.glyph = data;
    }

    /**
     * Add another number to this one
     * @param number
     */
    public void add(Dodecimal number) {
        int[] result = new int[Math.max(number.glyph.length, this.glyph.length)];
        int carry = 0;
        for (int i = 1; i <= result.length; i++) {
            int j = 0;
            int k = 0;

            if (i > this.glyph.length) k = number.glyph[number.glyph.length - i];
            else if (i > number.glyph.length) j = this.glyph[this.glyph.length - i];
            else {
                j = this.glyph[this.glyph.length - i];
                k = number.glyph[number.glyph.length - i];
            }

            int sum = j + k + carry;
            carry = sum / base;
            result[result.length - i] = sum % base;
        }

        if (carry == 1) {
            int[] resize = new int[result.length + 1];
            resize[0] = 1;
            for (int i = 1; i < resize.length; i++) {
                resize[i] = result[i-1];
            }
            result = resize;
        }
        this.glyph = result;
    }

    /**
     * Add one to this number
     */
    public void increment() {
        this.add(new Dodecimal(1));
    }

    /**
     * Convert the dodecimal into a string
     * @return string representing to dodecimal number
     */
    @Override
    public String toString() {
    StringBuilder string = new StringBuilder(glyph.length);
    for (int i = 0; i < glyph.length; i++) {
        string.append(digits[glyph[i]]);
    }
    return string.toString();
    }

    /**
     * Compare the value of both numbers
     * @param dodecimal base 12 number
     * @return true if both numbers possess the same value
     */
    public boolean equals(Dodecimal dodecimal) {
        return Arrays.equals(glyph, dodecimal.glyph);
    }

    /**
     * Convert a base 12 number into base 10
     * @return base 10 number
     */
    public int toInt() {
        int sum = 0;
        for (int i = 0; i < glyph.length; i++) {
            sum += glyph[glyph.length-i-1] * Math.pow(base, i);
        }
        return sum;
    }

    /**
     * Find the character of a specified digit
     * @param digit between 0 and 12
     * @return character representing digit
     */
    private int indexOf(char digit) {
        for (int i = 0; i < digits.length; i++) {
            if (digit == digits[i]) return i;
        }
        return -1;
    }

    /**
     * Creates a new object with the same value
     * @return new dodecimal number with the same value
     */
    @Override
    public Dodecimal clone() {
        return new Dodecimal(glyph);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
