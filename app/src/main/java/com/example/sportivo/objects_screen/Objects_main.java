package com.example.sportivo.objects_screen;

import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.sportivo.R;

public class Objects_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.os_activity_objects);
        ListView myListView =(ListView) findViewById(R.id.object_list);
        DataStorage.fillData();
        myListView.setAdapter((ListAdapter) new S4_Adapter(getApplicationContext()));
    }
}
