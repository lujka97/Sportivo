package com.example.sportivo.start_screen;

import android.content.Context;

public class Frag1_Sports {
        private int ID;
        private String name;

        public Frag1_Sports(int ID,String name){
            this.ID=ID;
            this.name=name;
        }
        public String getName(){
            return name;
        }
        public int getTmbImageId(Context context)
        {
            return context.getResources().getIdentifier("image_" +ID+ "_tmb","drawable",context.getPackageName());
        }

}
