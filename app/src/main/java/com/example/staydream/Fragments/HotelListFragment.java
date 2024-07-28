package com.example.staydream.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.staydream.Adapters.HotelAdapter;
import com.example.staydream.Data.HotelDataManager;
import com.example.staydream.Interface.OnDataLoad;
import com.example.staydream.Models.Hotel;
import com.example.staydream.Models.HotelList;
import com.example.staydream.R;
public class HotelListFragment extends Fragment {

    private RecyclerView fragment_LST_hotels;
    private String searchQuery;
    private HotelAdapter hotelAdapter;
    private HotelList filteredHotels;
    public HotelListFragment() {
        // Required empty public constructor
    }

    public HotelListFragment(String searchQuery){
        this.searchQuery = searchQuery;
        filteredHotels = new HotelList();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_hotel_list, container, false);
        findViews(v);
        initViews();
        loadData();
        return v;
    }

    private void findViews(View v) {
        fragment_LST_hotels = v.findViewById(R.id.fragment_LST_hotels);
    }

    private void initViews(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(),2);
        fragment_LST_hotels.setLayoutManager(gridLayoutManager);

    }

    private void loadData() {
        HotelDataManager.getInstance().setOnDataLoad(new OnDataLoad() {
            @Override
            public void dataLoaded(HotelList hotelList) {
                filterHotelSearch(hotelList);
                requireActivity().runOnUiThread(() -> {
                    hotelAdapter = new HotelAdapter(requireContext(),filteredHotels.getHotelList());
                    fragment_LST_hotels.setAdapter(hotelAdapter);
                });
            }
        });
        HotelDataManager.getInstance().loadHotelListDB();
    }

    private void filterHotelSearch(HotelList hotelList){
        if(hotelList == null)
            return;

        if(searchQuery == null || searchQuery.isEmpty()){
            filteredHotels = hotelList;
            return;
        }

        Hotel hotelCheck;
        for (int i = 0; i < hotelList.getHotelList().size(); i++) {
            hotelCheck = hotelList.getHotelList().get(i);
            if(hotelCheck.getHotel_name().toLowerCase().contains(searchQuery.toLowerCase()) || hotelCheck.getCountry().toLowerCase().contains(searchQuery.toLowerCase())
                    || hotelCheck.getCity().toLowerCase().contains(searchQuery.toLowerCase())){
                filteredHotels.addHotel(hotelCheck);
            }
        }
        Log.d("filtered -->", "filterHotelSearch: " + filteredHotels.toString());

    }
}