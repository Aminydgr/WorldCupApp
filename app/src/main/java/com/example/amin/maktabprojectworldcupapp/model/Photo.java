package com.example.amin.maktabprojectworldcupapp.model;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.UUID;

/**
 * Created by Amin on 8/16/2018.
 */

@Entity
public class Photo {

    @Id(autoincrement = true)
    private Long id;

    @Convert(converter = Photo.UUIDConverter.class, columnType = String.class)
    private UUID uuid;

    private String path;

    public Photo(UUID uuid, String path) {
        this.uuid = uuid;
        this.path = path;
    }

    public Photo(String path) {
        this.uuid = UUID.randomUUID ();
        this.path = path;
    }

    @Generated(hash = 2075189335)
    public Photo(Long id, UUID uuid, String path) {
        this.id = id;
        this.uuid = uuid;
        this.path = path;
    }


    @Generated(hash = 1043664727)
    public Photo() {
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


    public String getPath() {
        return this.path;
    }


    public void setPath(String path) {
        this.path = path;
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
