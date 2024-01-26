package ru.otus.crm.mappers;

import ru.otus.crm.entities.Address;
import ru.otus.crm.entities.Client;
import ru.otus.crm.entities.Phone;
import ru.otus.crm.model.ClientModel;

import java.util.Arrays;

public class Mapper {

    public static Client fromModel(ClientModel clientModel) {
        return new Client(
                clientModel.getName(),
                new Address(clientModel.getAddress(), null),
                Arrays.stream(clientModel.getPhones().split(",")).map(number -> new Phone(number, null)).toList());
    }

    private Mapper() {
    }
}
