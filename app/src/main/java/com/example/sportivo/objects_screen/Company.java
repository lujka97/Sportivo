package com.example.sportivo.objects_screen;

import com.example.sportivo.Court;

import java.util.ArrayList;

public class Company {

    private String companyName;
    private int companyId;
    private ArrayList<Court> courts;
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

    public ArrayList<Court> getCourts() {
        return courts;
    }
}

