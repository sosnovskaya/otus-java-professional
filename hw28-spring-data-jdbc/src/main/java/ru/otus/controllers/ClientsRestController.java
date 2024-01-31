package ru.otus.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.crm.entities.Client;
import ru.otus.crm.mappers.Mapper;
import ru.otus.crm.model.ClientModel;
import ru.otus.crm.service.ClientsService;

import java.util.List;

@RestController
public class ClientsRestController {

    private final ClientsService clientsService;

    public ClientsRestController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @GetMapping("/api/clients")
    public List<Client> getClientByName() {
        return clientsService.findAll();
    }

    @PostMapping("/api/clients")
    public Client saveClient(@RequestBody ClientModel clientModel) {
        return clientsService.saveClient(clientModel);
    }
}
