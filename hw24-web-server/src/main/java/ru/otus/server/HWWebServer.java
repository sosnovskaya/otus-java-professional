package ru.otus.server;

@SuppressWarnings({"squid:S112"})
public interface HWWebServer {
    void start() throws Exception;

    void join() throws Exception;

    void stop() throws Exception;
}
