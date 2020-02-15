package com.example.sportivo;

import java.time.LocalDateTime;
import java.util.Date;

public class Reservation {
    private int reservationId;
    private int userId;
    private int courtId;
    private Court court;
    private Date startTime;
    private Date endTime;

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public int getCourtId(){ return courtId; }

    public Court getCourt(){ return court; }

    public int getReservationId(){return reservationId;}
}
