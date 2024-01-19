package ru.numbers.client;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.numbers.NumbersResponse;

public class ClientStreamObserver implements StreamObserver<NumbersResponse> {
    private static final Logger log = LoggerFactory.getLogger(ClientStreamObserver.class);
    private long lastValue = 0;
    @Override
    public void onNext(NumbersResponse value) {
        log.info("new value:{}", value.getNumber());
        setLastValue(value.getNumber());
    }
    @Override
    public void onError(Throwable t) {
        log.error("got error", t);
    }

    @Override
    public void onCompleted() {
        log.info("request completed");
    }

    private synchronized void setLastValue(long value) {
        this.lastValue = value;
    }

    public synchronized long getLastValueAndReset() {
        var lastValuePrev = this.lastValue;
        this.lastValue = 0;
        return lastValuePrev;
    }
}
