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

    public Hotel() {
    }

    public Hotel(String name, Long rooms, Long reservedRooms) {
        this.name = name;
        this.rooms = rooms;
        this.reservedRooms = reservedRooms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRooms() {
        return rooms;
    }

    public void setRooms(Long rooms) {
        this.rooms = rooms;
    }

    public Long getReservedRooms() {
        return reservedRooms;
    }

    public void setReservedRooms(Long reservedRooms) {
        this.reservedRooms = reservedRooms;
    }
}
