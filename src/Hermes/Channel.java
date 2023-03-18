package Hermes;

/**
 * Channel is a use to send {@link Request} between different subfunctions.
 * On one hand, this channel will create a request, send it to {@link Hermes} where it will be kept until the recipient retrieve it.
 * Once a request as been processed, a {@link Reply} will be sent to Hermes where it will be kept until the send retrieve the reply.
 */
public class Channel extends Thread{
    private int revalidate = 25;
    private Object owner;

    /**
     *
     * @param owner Subordinate function
     */
    public Channel(Object owner) {
        super(owner.getClass().getName() + " Channel");
        this.owner = owner;
        start();
    }

    /**
     * Creates a new request with this object as the sender
     * @param recipients subordinate function that will receive a protocol
     * @return request
     */
    public Request generate(Class<?>... recipients) {
        return new Request(recipients, owner.getClass());
    }

    /**
     * Retrieve constantly from Hermes {@link Protocol} so that they can be processed. A reply will be sent
     */
    @Override
    public void run() {
        while (true) {
            Protocol protocol = Hermes.getProtocol(owner.getClass());
            if (protocol != null) {
                protocol.getTimeFrame().save();
                Result result = open(protocol);
                protocol.getTimeFrame().save();
                respond(result);
            } else {
                try {
                    sleep(revalidate);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Responds to a protocol with the requested data
     * @param result requested data
     */
    public void respond(Result result) {
        Hermes.send(result);
    }

    /**
     * Processes a protocol and extract the requested data
     * @param protocol
     * @return requested data
     */
    private Result open(Protocol protocol) {
        Result result = new Result(new Object[protocol.getSize()], protocol.getReference(), protocol.getPack(), protocol.getLocation(), protocol.getTimeFrame());
        for (int i = 0; i < protocol.getSize(); i++) {
            Query query = protocol.getQuery(i);
            result.add(query.invoke(owner), i);
        }
        return result;
    }

    /**
     * Sends a request and wait for a reply.
     * @param request
     * @return reply to request
     */
    public Reply send(Request request) {
        Hermes.send(request);
        Reply reply = null;
        while (reply == null) {
            try {
                sleep(revalidate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            reply = Hermes.getReply(request.getReference());
        }
        reply.getTimeFrame().save();
        return reply;
    }
}
