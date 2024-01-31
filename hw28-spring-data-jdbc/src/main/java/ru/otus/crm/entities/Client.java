package ru.otus.crm.entities;


import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@ToString
@Getter
@Table("client")
public class Client {

    @Id
    private final Long id;

    @Nonnull
    private final String name;

    @Nonnull
    @MappedCollection(idColumn = "client_id")
    private final Address address;

    @Nonnull
    @MappedCollection(idColumn = "client_id", keyColumn = "order_column")
    private final List<Phone> phones;

    public Client(@Nonnull String name, @Nonnull Address address, @Nonnull List<Phone> phones) {
        this(null, name, address, phones);
    }

    @PersistenceCreator
    public Client(Long id, @Nonnull String name, @Nonnull Address address, @Nonnull List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }
}
