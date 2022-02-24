package com.doubleclick.e_commerceapp.Model;


public class Admin {

    public Admin() {
    }

    public Admin(String name, String password, String phone, String max, String min, String email) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.max = max;
        this.min = min;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    private String name;
    private String password;
    private String phone;
    private String max;
    private String min;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", max='" + max + '\'' +
                ", min='" + min + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
