package ru.otus.crm.service;

import ru.otus.crm.entities.Client;
import ru.otus.crm.model.ClientModel;

import java.util.List;

public interface ClientsService {

    Client saveClient(ClientModel clientModel);

    List<Client> findAll();
}
