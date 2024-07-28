package com.example.staydream.Data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.staydream.Models.Hotel;
import com.example.staydream.Models.HotelList;
import com.example.staydream.Interface.OnDataLoad;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HotelDataManager {

   private HotelList hotelList;

   private static volatile HotelDataManager instance = null;
   private OnDataLoad onDataLoad;

   public HotelDataManager(){
       this.hotelList = new HotelList();
   }

    public void setOnDataLoad(OnDataLoad onDataLoad) {
        this.onDataLoad = onDataLoad;
    }

    public void loadHotelListDB(){
       DatabaseReference hotelRef = FirebaseDatabase.getInstance().getReference("hotels");
       hotelRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               hotelList = new HotelList();
               for(DataSnapshot hotelSnapshot : snapshot.getChildren()){
                   Hotel hotel = hotelSnapshot.getValue(Hotel.class);
                   if(hotel != null){
                       hotelList.addHotel(hotel);

                   }
               }
              onDataLoad.dataLoaded(hotelList);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               //pass
           }
       });
   }

   public static HotelDataManager init(Context context){
       if(instance == null){
           synchronized (HotelDataManager.class){
               if(instance == null)
                   instance = new HotelDataManager();
           }
       }
       return  getInstance();
   }

   public static HotelDataManager getInstance(){
       return instance;
   }

}
