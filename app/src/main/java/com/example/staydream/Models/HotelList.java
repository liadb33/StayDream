package com.example.staydream.Models;

import android.content.Context;
import android.util.Log;

import com.example.staydream.Utilities.LocationManager;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HotelList {

    private ArrayList<Hotel> hotelList;

    public HotelList(){
        hotelList = new ArrayList<>();
    }
    public HotelList addHotel(Hotel hotel){
        this.hotelList.add(hotel);
        return this;
    }

    public ArrayList<Hotel> getHotelList() {
        return hotelList;
    }

    public HotelList setHotelList(ArrayList<Hotel> hotelList) {
        this.hotelList = hotelList;
        return this;
    }

    public void addHotelPrice(){
        for (int i = 0; i < hotelList.size(); i++)
            hotelList.get(i).setPrice((int) (800 + (Math.random() * (2000 - 800))));
    }


    @Override
    public String toString() {
        return "HotelList{" +
                "hotelList=" + hotelList +
                '}';
    }
}
