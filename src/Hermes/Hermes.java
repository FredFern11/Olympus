package Hermes;

/**
 * Subordinate function in charge of communications. It acts as a database for {@link Request} and {@link Reply}.
 */
public class Hermes {
    public static String name = "Hermes";
    private static Inbox inbox = new Inbox();
    public static Responses responses = new Responses();

    public static Protocol getProtocol(Class<?> recipient) {
        return inbox.retrieve(recipient);
    }

    public static Reply getReply(Reference reference) {
        return responses.request(reference);
    }

    public static synchronized void send(Request request) {
        request.getTimeFrame().save();
        responses.create(request.getReference(), request.getSize(), request.getTimeFrame());
        inbox.store(request);
    }

    public static synchronized void send(Result result) {
        responses.store(result);
    }
}
