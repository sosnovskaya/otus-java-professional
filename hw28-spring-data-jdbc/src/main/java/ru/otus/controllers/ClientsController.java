package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.crm.entities.Client;
import ru.otus.crm.model.ClientModel;
import ru.otus.crm.service.ClientsService;

import java.util.List;

@Controller
public class ClientsController {

    private final ClientsService clientsService;

    public ClientsController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @GetMapping("/")
    public String clients() {
        return "clients";
    }

    @GetMapping("/clients/create")
    public String clientsCreateView(Model model) {
        model.addAttribute("client", new ClientModel());
        return "clientCreate";
    }

    @GetMapping( "/clients")
    public String clientsListView(Model model) {
        List<Client> clients = clientsService.findAll();
        model.addAttribute("clients", clients);
        return "clientsList";
    }
}
