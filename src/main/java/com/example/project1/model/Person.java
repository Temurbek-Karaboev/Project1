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
    @Column("room_id")
    private Long roomId;
    private String role;
    private String step;

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public Person() {
    }

    public Person(String name, String password, String phoneNumber, String role, String step) {
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.step = step;
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

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
