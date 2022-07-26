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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    public Room() {
    }

    public Room(String type, Long price, Long hotelId, Long userId, Long startDate, Long endDate, boolean isReserved) {
        this.type = type;
        this.price = price;
        this.hotelId = hotelId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isReserved = isReserved;
    }
}
