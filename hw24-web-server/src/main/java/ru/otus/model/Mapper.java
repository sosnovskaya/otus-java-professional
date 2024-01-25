package ru.otus.model;

import ru.otus.jpql.hw.crm.model.Address;
import ru.otus.jpql.hw.crm.model.Client;
import ru.otus.jpql.hw.crm.model.Phone;

import java.util.List;

public class Mapper {

    public static ClientDTO map(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setName(client.getName());
        clientDTO.setAddress(map(client.getAddress()));
        List<PhoneDTO> phones = client.getPhones() != null ? client.getPhones().stream().map(Mapper::map).toList() : null;
        clientDTO.setPhones(phones);
        return clientDTO;
    }

    private static AddressDTO map(Address address) {
        return address != null ? new AddressDTO(address.getId(), address.getStreet()) : null;
    }

    private static PhoneDTO map(Phone phone) {
        return phone != null ? new PhoneDTO(phone.getId(), phone.getNumber()) : null;
    }
}
