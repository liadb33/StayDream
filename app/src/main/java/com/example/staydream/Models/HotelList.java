package com.example.staydream.Models;

import android.content.Context;
import android.util.Log;

import com.example.staydream.Utilities.LocationManager;

import java.util.ArrayList;

public class HotelList {

    private ArrayList<Hotel> hotelList = new ArrayList<>();

    public HotelList(){

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


    @Override
    public String toString() {
        return "HotelList{" +
                "hotelList=" + hotelList +
                '}';
    }
}
