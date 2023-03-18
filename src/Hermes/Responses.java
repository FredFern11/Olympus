package Hermes;

import Apollo.TimeFrame;

import java.util.HashMap;

/**
 * Repository storing the {@link Reply}. Once a {@link Request} has been received by {@link Hermes}, a new empty
 * reply is created. When this reply possesses all the {@link Result} from the multiple recipients, it will be given
 * to the subordinate function that requested the data.
 */
public class Responses {
    private HashMap<Reference, Reply> repository = new HashMap<>();

    public void store(Result result) {
        Reply reply = repository.get(result.getPack());
        reply.add(result, result.getLocation());
    }

    public Reply request(Reference reference) {
        Reply reply = repository.get(reference);
        return reply.full() ? repository.remove(reference) : null;
    }

    /**
     * Once a request as been recieved by {@link Hermes}, a reply is created
     * @param reference request's reference
     * @param size number of protocols in the request
     * @param timeFrame request's timeframe
     */
    public void create(Reference reference, int size, TimeFrame timeFrame) {
        repository.put(reference, new Reply(reference, new Result[size], timeFrame));
    }
}
