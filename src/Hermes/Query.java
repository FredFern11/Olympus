package Hermes;

import Apollo.TimeFrame;

import java.lang.reflect.Method;
import java.util.Arrays;

import static Apollo.Apollo.findMethod;

/**
 * Smallest communication block. A query ask a subordinate function to perform one of its methods and ask for the result.
 */
public class Query {
    private final Method method;
    private final Object[] args;

    private String clearance;
    private Boolean urgency;

    private final Reference reference;
    private final Reference pack;
    private TimeFrame timeFrame;

    public Query(Reference pack, Class<?> recipient, String methodName, Object... args) {
        this.method = findMethod(methodName, recipient);
        // TODO: 2022-12-13 changed that check if works properly 
        this.args = args.clone();
        this.reference = new Reference();
        this.pack = pack;
    }

    /**
     * Perform the query with its arguments.
     * @param object subordinate function on which the method will be performed
     * @return the result of performing this method
     */
    public Object invoke(Object object) {
        try {
            return method.invoke(object, args);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(method.getName());
            System.exit(89);
        }
        return null;
    }

    public Reference getReference() {
        return reference;
    }

    public Reference getPack() {
        return pack;
    }

    @Override
    public String toString() {
        return method.getName() + Arrays.toString(args) + " <" + reference + ">";
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Check if the query is equal to this object.
     * @return if the two object possess the same {@link Reference}
     */
    @Override
    public boolean equals(Object object) {
        if (object == null || object.getClass() != getClass()) return false;
        return this.reference.equals(((Query) object).getReference());
    }
}
