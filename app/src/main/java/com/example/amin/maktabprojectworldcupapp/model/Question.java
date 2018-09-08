package com.example.amin.maktabprojectworldcupapp.model;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.UUID;

/**
 * Created by Amin on 8/17/2018.
 */
@Entity
public class Question {

    @Id(autoincrement = true)
    private Long id;

    @Convert(converter = UUIDConverter.class, columnType = String.class)
    private UUID uuid;

    private String text;

    public Question() {
        this.uuid = UUID.randomUUID ();
    }

    public Question(UUID uuid, String text) {
        this.uuid = uuid;
        this.text = text;
    }

    @Generated(hash = 489615652)
    public Question(Long id, UUID uuid, String text) {
        this.id = id;
        this.uuid = uuid;
        this.text = text;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static class UUIDConverter implements PropertyConverter<UUID, String> {
        @Override
        public UUID convertToEntityProperty(String databaseValue) {
            if (databaseValue == null)
                return null;

            return UUID.fromString ( databaseValue );
        }

        @Override
        public String convertToDatabaseValue(UUID entityProperty) {
            return entityProperty.toString ();
        }
    }

}
