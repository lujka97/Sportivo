package com.example.sportivo;

import java.time.LocalDateTime;
import java.util.Date;

public class Reservation {
    private int reservationId;
    private int userId;
    private int courtId;
    private Date startTime;
    private Date endTime;

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }
}
