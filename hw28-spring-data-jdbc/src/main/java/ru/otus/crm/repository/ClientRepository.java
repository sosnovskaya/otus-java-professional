package ru.otus.crm.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.otus.crm.entities.Client;

import java.util.List;

public interface ClientRepository extends ListCrudRepository<Client, Long> {
    List<Client> findAll();
}
