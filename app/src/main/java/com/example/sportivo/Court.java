package com.example.sportivo;

import com.example.sportivo.objects_screen.Company;

public class Court {
    private int courtId;
    private String courtName;
    private int sportId;
    private int companyId;
    private Company company;

    public int getCourtId() {
        return courtId;
    }

    public String getCourtName(){
        return courtName;
    }

    public Company getCompany() { return company; }
    public int getSportId() { return sportId ;}
}
