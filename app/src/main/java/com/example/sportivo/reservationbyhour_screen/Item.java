package com.example.sportivo.reservationbyhour_screen;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {

    public final String teren;
    public final String price;

    public Item(String time, String price) {
        this.teren=time;
        this.price=price;
    }

    protected Item(Parcel in) {
        teren = in.readString();
        price = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(teren);
        dest.writeString(price);
    }
}
