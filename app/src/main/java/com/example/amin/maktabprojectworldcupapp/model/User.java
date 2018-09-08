package com.example.amin.maktabprojectworldcupapp.model;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.UUID;

/**
 * Created by Amin on 8/9/2018.
 */

@Entity
public class User {

    @Id(autoincrement = true)
    private Long id;

    @Convert(converter = UUIDConverter.class, columnType = String.class)
    private UUID uuid;

    private String name;
    private String phoneNumber;
    private String password;
    private String photoPath;

    private boolean admin;


    public User() {
        this.uuid = UUID.randomUUID ();
    }

    public User(String name, String phoneNumber, String password, boolean admin) {
        this.uuid = UUID.randomUUID ();
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.admin = admin;
    }

    public User(UUID uuid, String name, String phoneNumber, String password, String photoPath) {
        this.uuid = uuid;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.photoPath = photoPath;
    }

    @Generated(hash = 696638648)
    public User(Long id, UUID uuid, String name, String phoneNumber,
                String password, String photoPath, boolean admin) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.photoPath = photoPath;
        this.admin = admin;
    }

    public User(UUID uuid, String name, String phoneNumber, String password, String photoPath, boolean admin) {
        this.uuid = uuid;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.photoPath = photoPath;
        this.admin = admin;
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhotoPath() {
        return this.photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public boolean getAdmin() {
        return this.admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
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
