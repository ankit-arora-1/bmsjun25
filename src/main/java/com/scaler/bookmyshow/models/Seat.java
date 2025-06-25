package com.scaler.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Seat extends BaseModel {
    private int row;
    private int col;

    @ManyToOne
    private SeatType seatType;
    private String number;
}
