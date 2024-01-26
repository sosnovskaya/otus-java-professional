package ru.otus.crm.entities;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Nonnull;
@ToString
@Getter
@Table("address")
public class Address   {

    @Id
    private final Long id;

    @Nonnull
    private final String address;

    private final String clientId;

    public Address(@Nonnull String address, String clientId) {
        this(null, address, clientId);
    }

    @PersistenceCreator
    public Address(Long id, @Nonnull String address, String clientId) {
        this.id = id;
        this.address = address;
        this.clientId = clientId;
    }
}
