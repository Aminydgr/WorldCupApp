package com.example.amin.maktabprojectworldcupapp.model;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.UUID;

/**
 * Created by Amin on 8/23/2018.
 */

@Entity
public class ChatRoom {

    @Id(autoincrement = true)
    private Long id;

    @Convert ( converter = UUIDConverter.class, columnType = String.class)
    private UUID uuid;

    private String title;

    public ChatRoom(UUID uuid, String title) {
        this.uuid = uuid;
        this.title = title;
    }

    @Generated(hash = 554298270)
    public ChatRoom(Long id, UUID uuid, String title) {
        this.id = id;
        this.uuid = uuid;
        this.title = title;
    }

    @Generated(hash = 507512638)
    public ChatRoom() {
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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
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
