package ru.otus.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.NumbersAlternator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import static ru.otus.utils.Utils.sleep;

public class MonitorNumbersAlternator implements NumbersAlternator {
    private static final Logger logger = LoggerFactory.getLogger(MonitorNumbersAlternator.class);
    private static final String FIRST = "First";
    private static final String SECOND = "Second";
    private static final List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 9, 8, 7, 6, 5, 4, 3, 2);
    private String turn = FIRST;

    @Override
    public void alternateNumbers() {
        new Thread(() -> action(new ArrayDeque<>(numbers), SECOND), FIRST).start();
        new Thread(() -> action(new ArrayDeque<>(numbers), FIRST), SECOND).start();
    }

    private synchronized void action(Deque<Integer> numbers, String next) {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                //spurious wakeup https://en.wikipedia.org/wiki/Spurious_wakeup
                //поэтому не if
                while (!turn.equals(Thread.currentThread().getName())) {
                    this.wait();
                }

                Integer currentNumber = numbers.poll();
                logger.info(String.valueOf(currentNumber));

                numbers.add(currentNumber);
                turn = next;
                sleep();

                notifyAll();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
