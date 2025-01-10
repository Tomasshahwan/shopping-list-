package com.example.pro2.models;

public class students {
    private String email;
    private String phone ;

    public students() {
    }

    public students(String phone, String email) {
        this.email = email;
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
