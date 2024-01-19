package ru.numbers.client;

import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.numbers.NumbersRequest;
import ru.numbers.NumbersServiceGrpc;

import static ru.numbers.ApplicationProperties.getServerHost;
import static ru.numbers.ApplicationProperties.getServerPort;

public class NumbersClient {
    private static final Logger log = LoggerFactory.getLogger(NumbersClient.class);
    private static final int FIRST_VALUE = 0;
    private static final int LAST_VALUE = 30;
    private static final int LOOP_LIMIT = 50;
    private long value = 0;

    public static void main(String[] args) {
        log.info("NumbersClient is starting...");

        var managedChannel = ManagedChannelBuilder.forAddress(getServerHost(), getServerPort())
                .usePlaintext()
                .build();

        var asyncClient = NumbersServiceGrpc.newStub(managedChannel);

        new NumbersClient().clientAction(asyncClient);

        log.info("NumbersClient is shutting down...");
        managedChannel.shutdown();
    }

    private void clientAction(NumbersServiceGrpc.NumbersServiceStub asyncClient) {
        var numbersRequest = makeNumberRequest();
        var clientSteamObserver = new ClientStreamObserver();
        asyncClient.number(numbersRequest, clientSteamObserver);

        for (var i = 0; i < LOOP_LIMIT; i++) {
            var valForPrint = getNextValue(clientSteamObserver);
            log.info("currentValue:{}", valForPrint);
            sleep();
        }
    }

    private long getNextValue(ClientStreamObserver clientStreamObserver) {
        value = value + clientStreamObserver.getLastValueAndReset() + 1;
        return value;
    }

    private NumbersRequest makeNumberRequest() {
        return NumbersRequest.newBuilder()
                .setFirstValue(FIRST_VALUE)
                .setLastValue(LAST_VALUE)
                .build();
    }

    private static void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
