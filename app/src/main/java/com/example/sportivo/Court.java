package com.example.sportivo;

import com.example.sportivo.objects_screen.Company;

public class Court {
    private int courtId;
    private String courtName;
    private int sportId;
    private int companyId;
    private Company company;
    private int price;

    public Court(String name, int sportId, int companyId, int price){
        this.courtName = name;
        this.sportId = sportId;
        this.companyId = companyId;
        this.price = price;
    }

    public Court(){

    }

    public int getCourtId() { return courtId; }
    public String getCourtName(){ return courtName; }
    public Company getCompany() { return company; }
    public int getSportId() { return sportId ;}
    public int getPrice() { return price; }
}
