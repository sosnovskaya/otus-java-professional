package ru.otus.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.jpql.hw.crm.model.Address;
import ru.otus.jpql.hw.crm.model.Client;
import ru.otus.jpql.hw.crm.model.Phone;
import ru.otus.jpql.hw.crm.service.DBServiceClient;
import ru.otus.model.ClientDTO;
import ru.otus.model.Mapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings({"squid:S1948"})
public class ClientsApiServlet extends HttpServlet {
    private static final String PARAM_NAME = "name";
    private static final String PARAM_ADDRESS = "address";
    private static final String PARAM_PHONES = "phones";
    private static final int ID_PATH_PARAM_POSITION = 1;
    private static DBServiceClient dbServiceClient;
    private final Gson gson;

    public ClientsApiServlet(DBServiceClient dbServiceClient, Gson gson) {
        this.dbServiceClient = dbServiceClient;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        List<ClientDTO> clients = dbServiceClient.findAll().stream().map(Mapper::map).toList();
        String resp = gson.toJson(clients);
        out.print(resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter(PARAM_NAME);
        String address = req.getParameter(PARAM_ADDRESS);
        String phonesParam = req.getParameter(PARAM_PHONES);
        List<Phone> phones = Arrays.stream(phonesParam.split(",")).map(it -> new Phone(null, it)).collect(Collectors.toList());
        dbServiceClient.saveClient(new Client(null, name, new Address(null, address), phones));
        resp.sendRedirect("/clients");
    }
}
