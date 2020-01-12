package com.example.sportivo.reservation_screen;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

public class Base extends ExpandableGroup<Item> {
    public Base(String title,List<Item> items) {

        super(title,items);
    }
}
