package com.example.sportivo.time_screen;

import android.app.Activity;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.NumberPicker;

import com.example.sportivo.R;

class PopUp extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ts_choose);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.6));
        NumberPicker numberpicker=findViewById(R.id.numpick);
        numberpicker.setMinValue(0);
        numberpicker.setMaxValue(23);
    }
}
