package Hermes;

import Apollo.TimeFrame;

import java.nio.Buffer;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Message sent to other subordinate functions. It contains {@link Protocol} that will help the recipients retrieve
 * the data ask by the sender. Once all the data is retrieved. a {@link Reply} will be sent to the sender along with
 * all the data requested.
 */
public class Request {
    private final Reference reference;
    private Protocol[] protocolList;
    private TimeFrame timeFrame;
    private final Class<?> sender;

    public Request(Class<?>[] recipients, Class<?> sender) {
        this.sender = sender;
        this.reference = new Reference();
        protocolList = new Protocol[recipients.length];
        timeFrame = new TimeFrame();
        for (int i = 0; i < recipients.length; i++) {
            Protocol protocol = new Protocol(recipients[i], reference);
            protocol.setLocation(i);
            protocolList[i] = protocol;
        }
    }

    /**
     * Add a query to as specific {@link Protocol}.
     * @param location index of the protocol
     * @param methodName name of the method as it is declared in the function
     * @param args arguments necessary to perform the method
     * @return this request to enable method chaining
     */
    public Request add(int location, String methodName, Object... args) {
        protocolList[location].add(methodName, args);
        return this;
    }


    public TimeFrame getTimeFrame() {
        return timeFrame;
    }

    public Protocol getProtocol(int i) {
        return protocolList[i];
    }

    public int getSize() {
        return protocolList.length;
    }

    public Reference getReference() {
        return reference;
    }


    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (Protocol protocol : protocolList) {
            string.append(protocol).append("\n");
        }
        return getClass().getSimpleName() + " <" + reference + ">\n" + string;
    }
}
