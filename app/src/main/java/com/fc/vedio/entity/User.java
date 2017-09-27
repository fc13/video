package com.fc.vedio.entity;

/**
 * @author 范超 on 2017/9/26
 */

public class User {
    private String name;
    private String password;
    private String gender;
    private String phone;

    public User(String name, String password, String gender, String phone) {
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.phone = phone;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
