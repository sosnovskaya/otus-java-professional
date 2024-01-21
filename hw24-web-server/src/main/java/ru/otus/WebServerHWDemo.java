package ru.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.otus.auth.InMemoryUserDao;
import ru.otus.auth.UserDao;
import ru.otus.jpql.hw.DBConfiguration;
import ru.otus.jpql.hw.crm.service.DBServiceClient;
import ru.otus.server.HWWebServer;
import ru.otus.server.HWWebServerWithSecurity;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.TemplateProcessorImpl;
import ru.otus.services.UserAuthService;
import ru.otus.services.UserAuthServiceImpl;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница клиентов
    http://localhost:8080/clients

    // REST сервис
    http://localhost:8080/api/clients
    http://localhost:8080/api/clients/3
*/
public class WebServerHWDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        DBServiceClient dbServiceClient = DBConfiguration.getDBServiceClient();
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        UserDao userDao = new InMemoryUserDao();
        UserAuthService authService = new UserAuthServiceImpl(userDao);

        HWWebServer webServer = new HWWebServerWithSecurity(WEB_SERVER_PORT, dbServiceClient, gson, templateProcessor, authService);

        webServer.start();
        webServer.join();
    }
}
