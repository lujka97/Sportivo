package com.example.sportivo.admin_screen;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.sportivo.R;
import com.google.android.material.tabs.TabLayout;

public class AdminMainActivity extends AppCompatActivity {
    AdminSectionsPageAdapter sectionsPageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main_activity);

        sectionsPageAdapter = new AdminSectionsPageAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.admin_view_pager);
        viewPager.setAdapter(sectionsPageAdapter);
        TabLayout tabs = findViewById(R.id.adminTabs);
        tabs.setupWithViewPager(viewPager);
    }
}
