package Hermes;

import Athena.Athena;
import Gaia.Gaia;
import Theia.Theia;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Like a usual inbox, it receives communication devices in the form of {@link Protocol}
 * that will be stored and then retrieved by the recipient.
 */
public class Inbox {
    private Queue<Protocol>[] matrix = new Queue[2];

    public Inbox() {
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = new LinkedList<>();
        }
    }

    /**
     * Retrieves the first protocol that belongs to the subordinate function
     * @param entity subordinate function
     * @return requested protocol
     */
    public Protocol retrieve(Class<?> entity) {
        int i = convert(entity);
        return matrix[i].poll();
    }

    /**
     * Stores a request to be retrieve later by the recipient
     * @param request
     */
    public void store(Request request) {
        int size = request.getSize();
        for (int i = 0; i < size; i++) {
            Protocol protocol = request.getProtocol(i);
            protocol.setTimeFrame();
            int n = convert(protocol.getRecipient());
            matrix[n].add(protocol);
        }
    }

    private static int convert(Class<?> recipient) {
        if (recipient.equals(Gaia.class)) return 0;
        else if (recipient.equals(Theia.class)) return 1;
        else if (recipient.equals(Athena.class)) return 2;
        return -1;
    }
}
