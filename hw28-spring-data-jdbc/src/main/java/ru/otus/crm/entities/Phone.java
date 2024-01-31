package ru.otus.crm.entities;


import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@ToString
@Getter
@Table("phone")
public class Phone {

    @Id
    private final Long id;

    @Nonnull
    private final String number;


    private final String clientId;

    public Phone(@Nonnull String number, String clientId) {
        this(null, number, clientId);
    }

    @PersistenceCreator
    public Phone(Long id, @Nonnull String number, String clientId) {
        this.id = id;
        this.number = number;
        this.clientId = clientId;
    }
}
