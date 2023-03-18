package Hermes;

import Apollo.TimeFrame;

import java.util.LinkedList;

/**
 * Container class for {@link Query}. A protocols possess only a single recipient, meaning that all
 * the queries that it contains must all share a common subordinate function.
 */
public class Protocol {
    private Class<?> recipient;

    private String clearance;
    private Boolean urgency;

    private Reference reference;
    private Reference pack;
    private int location;

    private TimeFrame timeFrame;
    private int size = 0;
    private LinkedList<Query> queryList = new LinkedList();

    public Protocol(Class<?> recipient, Reference pack) {
        this.recipient = recipient;
        this.reference = new Reference();
        this.pack = pack;
    }

    /**
     * Add a {@link Query} to the protocol.
     * @param methodName name of the method as it is written in the class declaration
     * @param args arguments needed to invoke the method
     * @return this protocol to enable method chaining
     */
    public Protocol add(String methodName, Object... args) {
        Query query = new Query(reference, recipient, methodName, args);
        queryList.add(query);
        size++;
        return this;
    }


    public Query getQuery(int i) {
        return queryList.get(i);
    }

    public int getSize() {
        return size;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getLocation() {
        return location;
    }

    public Class<?> getRecipient() {
        return recipient;
    }

    public Reference getReference() {
        return reference;
    }

    public Reference getPack() {
        return pack;
    }

    public TimeFrame getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame() {
        timeFrame = new TimeFrame();
    }


    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        String prefix = recipient.getSimpleName() + " <" + reference + ">" + " {";
        String blank = prefix.replaceAll(".", " ");
        string.append(prefix);

        int counter = 0;
        int size = queryList.size();

        for (Query query : queryList) {
            string.append((counter == 0 ? "" : blank) + query + (++counter < size ? "\n" : "}\n"));
        }
        return string.toString();
    }
}
