package com.example.sportivo.objects_screen;

public class Objects {

    private String companyName;
    private int companyId;
    public Objects(int ID, String name){

        this.companyName=name;
        this.companyId=ID;
    }
    public String getName(){
        return this.companyName;
    }
    public int getId(){
        return this.companyId;
    }

}

