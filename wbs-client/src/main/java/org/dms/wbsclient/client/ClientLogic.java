package org.dms.wbsclient.client;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import reactor.core.publisher.Flux;

public class ClientLogic {

    private final static AtomicInteger MESSAGE_ID;
    static {
        MESSAGE_ID = new AtomicInteger(0);
    }
    public void doLogic(Client client, long sendInterval) {
        Flux.interval(Duration.ofSeconds(sendInterval))
            .doOnNext(n -> client.send("Test message " + MESSAGE_ID.getAndIncrement()))
            .subscribe();
    }
}
