package ru.numbers;

public class ApplicationProperties {
    private static final int SERVER_PORT = 8190;
    private static final String SERVER_HOST = "localhost";

    public static String getServerHost() {
        return SERVER_HOST;
    }

    public static int getServerPort() {
        return SERVER_PORT;
    }

    private ApplicationProperties() {
    }
}
