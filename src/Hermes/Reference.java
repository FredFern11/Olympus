package Hermes;

import Apollo.Dodecimal;

/**
 * Tag to identify different objects based on their creation date.
 */
public class Reference {
    private static Dodecimal index = new Dodecimal(0);
    private Dodecimal identifier;

    /**
     * Create a new reference with a value one more than the previous reference created
     */
    public Reference() {
        this.identifier = index.clone();
        index.increment();
    }

    public Reference(Dodecimal identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return identifier.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public Reference clone() {
        return new Reference(identifier);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || object.getClass() != getClass()) return false;
        return identifier.equals(((Reference) object).identifier);
    }
}
