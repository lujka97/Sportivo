package com.example.sportivo.Models;

import com.example.sportivo.Models.Court;

import java.util.ArrayList;

public class Company {

    private String companyName;
    private int companyId;
    private ArrayList<Court> courts;
    private int opens;
    private int closes;

    public Company(int ID, String name){

        this.companyName=name;
        this.companyId=ID;
    }
    public String getName(){
        return this.companyName;
    }
    public int getId(){
        return this.companyId;
    }
    public int getOpens() { return this.opens; }
    public int getCloses() { return this.closes; }
    public int getCompanyId() { return this.companyId; }

    public ArrayList<Court> getCourts() {
        return courts;
    }
}

