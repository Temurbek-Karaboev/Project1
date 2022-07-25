package com.example.project1.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

public class Hotel {
    @Id
    private Long id;
    private String name;
    private Long rooms;
    @Column("reserved_rooms")
    private Long reservedRooms;
}
