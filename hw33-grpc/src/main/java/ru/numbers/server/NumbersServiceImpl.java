package ru.numbers.server;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.numbers.NumbersRequest;
import ru.numbers.NumbersResponse;
import ru.numbers.NumbersServiceGrpc;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class NumbersServiceImpl extends NumbersServiceGrpc.NumbersServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(NumbersServiceImpl.class);
    @Override
    public void number(NumbersRequest request, StreamObserver<NumbersResponse> responseObserver) {
        log.info("request for new sequence of numbers, firstValue:{}, lastValue:{}", request.getFirstValue(), request.getLastValue());
        var currentValue = new AtomicLong(request.getFirstValue());
        var executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            var value = currentValue.incrementAndGet();
            var response = NumbersResponse.newBuilder().setNumber(value).build();
            responseObserver.onNext(response);
            if(value == request.getLastValue()) {
                executor.shutdown();
                responseObserver.onCompleted();
                log.info("sequence of numbers finished");
            }
        };
        executor.scheduleAtFixedRate(task, 0, 2, TimeUnit.SECONDS);
    }
}
