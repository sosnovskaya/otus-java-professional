package ru.otus;

import ru.otus.monitor.MonitorNumbersAlternator;

public class Main {
    public static void main(String[] args) {
        NumbersAlternator alternator;
        alternator = new MonitorNumbersAlternator();

        alternator.alternateNumbers();
    }
}
