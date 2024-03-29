package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.crm.entities.Client;
import ru.otus.crm.mappers.Mapper;
import ru.otus.crm.model.ClientModel;
import ru.otus.crm.repository.ClientRepository;
import ru.otus.sessionmanager.TransactionManager;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientsServiceImpl implements ClientsService {
    private static final Logger log = LoggerFactory.getLogger(ClientsServiceImpl.class);

    private final TransactionManager transactionManager;
    private final ClientRepository clientRepository;

    public ClientsServiceImpl(TransactionManager transactionManager, ClientRepository clientRepository) {
        this.transactionManager = transactionManager;
        this.clientRepository = clientRepository;
    }

    @Override
    public Client saveClient(ClientModel clientModel) {
        Client client = Mapper.fromModel(clientModel);
        return transactionManager.doInTransaction(() -> {
            var savedClient = clientRepository.save(client);
            log.info("saved client: {}", savedClient);
            return savedClient;
        });
    }

    @Override
    public List<Client> findAll() {
        var clientList = new ArrayList<Client>();
        clientRepository.findAll().forEach(clientList::add);
        log.info("clientList:{}", clientList);
        return clientList;
    }
}
