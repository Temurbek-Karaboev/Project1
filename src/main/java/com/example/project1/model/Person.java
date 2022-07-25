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
}
