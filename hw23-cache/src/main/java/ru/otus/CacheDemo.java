package ru.otus;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.jdbc.core.repository.executor.DbExecutorImpl;
import ru.otus.jdbc.core.sessionmanager.TransactionRunnerJdbc;
import ru.otus.jdbc.crm.datasource.DriverManagerDataSource;
import ru.otus.jdbc.crm.model.Client;
import ru.otus.jdbc.crm.repository.ClientDataTemplateJdbc;
import ru.otus.jdbc.crm.service.DBServiceClient;
import ru.otus.jdbc.crm.service.DbServiceClientImpl;

import javax.sql.DataSource;
import java.util.List;

public class CacheDemo {
    private static final Logger log = LoggerFactory.getLogger(CacheDemo.class);
    private static final String URL = "jdbc:postgresql://localhost:5430/demoDB";
    private static final String USER = "usr";
    private static final String PASSWORD = "pwd";
    private static DBServiceClient dbServiceClient;
    private static DBServiceClient dbServiceClientCached;

    public static void main(String[] args) {
        init();
        Client client = new Client("dbServiceSecond");
        var savedClient = dbServiceClientCached.saveClient(client);

        long begin = System.currentTimeMillis();
        dbServiceClient.getClient(savedClient.getId());
        long time = System.currentTimeMillis() - begin;

        begin = System.currentTimeMillis();
        dbServiceClientCached.getClient(savedClient.getId());
        long timeCached = System.currentTimeMillis() - begin;

        log.info("time from db: " + time);
        log.info("time from cache: " + timeCached);
    }

    private static void init() {
        var dataSource = new DriverManagerDataSource(URL, USER, PASSWORD);
        flywayMigrations(dataSource);
        var transactionRunner = new TransactionRunnerJdbc(dataSource);
        var dbExecutor = new DbExecutorImpl();
        var clientTemplate = new ClientDataTemplateJdbc(dbExecutor);
        dbServiceClient = new DbServiceClientImpl(transactionRunner, clientTemplate);
        dbServiceClientCached = new DbServiceClientCachedImpl(transactionRunner, clientTemplate);
    }

    private static void flywayMigrations(DataSource dataSource) {
        log.info("db migration started...");
        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/db/migration")
                .load();
        flyway.migrate();
        log.info("db migration finished.");
        log.info("***");
    }
}
