package com.example.project1.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

public class Room {
    @Id
    private Long id;
    private String type;
    private Long price;
    @Column("hotel_id")
    private Long hotelId;
    @Column("user_id")
    private Long userId;
    @Column("start_date")
    private Long startDate;
    @Column("end_date")
    private Long endDate;
    @Column("reserved")
    private boolean isReserved;
}
