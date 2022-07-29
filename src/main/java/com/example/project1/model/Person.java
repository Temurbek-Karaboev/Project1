package com.example.project1.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
public class Person {
    @Id
    private Long id;
    private String name;
    private String password;
    @Column("phone_number")
    private String phoneNumber;
    private String role;
    public Person() {
    }
    public Person(String name, String password, String phoneNumber, String role) {
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
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
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
