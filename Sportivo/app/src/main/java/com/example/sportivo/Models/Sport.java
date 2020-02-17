package com.example.sportivo.Models;

import android.content.Context;

public class Sport {
        private int sportId;
        private String name;
        private String imageURL;

        public Sport(int ID, String name, String url){
            this.sportId = ID;
            this.name = name;
            this.imageURL = url;
        }
        public String getName(){
            return name;
        }
        public int getId() {return this.sportId;}

        public String getImageUrl() {
            return imageURL;
        }

    public int getTmbImageId(Context context)
        {
            return context.getResources().getIdentifier("image_" +1+ "_tmb","drawable",context.getPackageName());
        }

}
