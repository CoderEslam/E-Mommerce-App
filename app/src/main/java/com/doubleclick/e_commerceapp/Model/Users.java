package com.doubleclick.e_commerceapp.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Users
{
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneOrder() {
        return phoneOrder;
    }

    public void setPhoneOrder(String phoneOrder) {
        this.phoneOrder = phoneOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name;
    private String email;
    private String password;
    private String image;
    private String address;
    private String phoneOrder;
    private String phone;
    private String service;
    @PrimaryKey(autoGenerate = true)
    private int id;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }



    public Users()
    {

    }

    public Users(String name, String email, String password, String image, String address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.image = image;
        this.address = address;
    }

    public Users(String name, String email, String password, String image, String address, String phoneOrder) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.image = image;
        this.address = address;
        this.phoneOrder = phoneOrder;
    }

    public Users(String name, String email, String password, String image, String address, String phoneOrder, int id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.image = image;
        this.address = address;
        this.phoneOrder = phoneOrder;
        this.id = id;
    }


    @Override
    public String toString() {
        return "Users{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", image='" + image + '\'' +
                ", address='" + address + '\'' +
                ", phoneOrder='" + phoneOrder + '\'' +
                '}';
    }
}

