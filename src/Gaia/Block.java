package Gaia;

import Apollo.Point;
/**
 * Simulate a block of substance with all its characteristics.
 */
public abstract class Block {
    public final Point position;

    public Block(Point position) {
        this.position = position;
    }

    /**
     * Get the value of the specified block attribute
     * @param attribute name of the field
     * @return the attribute's level
     */
    public int get(String attribute) {
        try {
            return (int) this.getClass().getDeclaredField(attribute).get(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Set a specific value to a block's attribute
     * @param attribute name of the field
     */
    public void set(String attribute, int value) {
        if (value > 100) {
            throw new RuntimeException("The " + attribute + " amount cannot be greater than 100");
        }
        try {
            getClass().getDeclaredField(attribute).set(this, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if two blocks are the same
     * @param object different bloock
     * @return if they are the same type of block and if they have the same position
     * @Warning: the same block coming from different time will be interpreted as the same block (return true)
     */
    @Override
    public boolean equals(Object object) {
        if (object == null || object.getClass() != this.getClass()) return false;
        Block block = (Block) object;
        return position.equals(block.position);
    }
}
