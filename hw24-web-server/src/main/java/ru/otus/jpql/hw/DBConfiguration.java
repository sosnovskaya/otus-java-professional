package ru.otus.jpql.hw;

import org.hibernate.cfg.Configuration;
import ru.otus.jpql.hw.core.repository.DataTemplateHibernate;
import ru.otus.jpql.hw.core.repository.HibernateUtils;
import ru.otus.jpql.hw.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.jpql.hw.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.jpql.hw.crm.model.Address;
import ru.otus.jpql.hw.crm.model.Client;
import ru.otus.jpql.hw.crm.model.Phone;
import ru.otus.jpql.hw.crm.service.DBServiceClient;
import ru.otus.jpql.hw.crm.service.DbServiceClientImpl;

public class DBConfiguration {
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static DBServiceClient getDBServiceClient() {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        ///
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
        ///
        return new DbServiceClientImpl(transactionManager, clientTemplate);
    }
}
