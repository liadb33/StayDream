package com.example.staydream.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.staydream.Adapters.HotelAdapter;
import com.example.staydream.Data.DataManager;
import com.example.staydream.Interface.OnDataLoadFavorite;
import com.example.staydream.Interface.OnDataLoadHotel;
import com.example.staydream.Models.Hotel;
import com.example.staydream.Models.HotelList;
import com.example.staydream.R;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class HotelListFragment extends Fragment {

    private String fromDate;
    private String toDate;
    private RecyclerView fragment_LST_hotels;
    private String searchQuery;
    private HotelAdapter hotelAdapter;
    private CircularProgressIndicator loading_indicator_hotel_view;
    private HotelList filteredHotels;
    private FirebaseUser user;
    private int numGuests;

    public HotelListFragment() {
        // Required empty public constructor
    }

    public HotelListFragment(String searchQuery, String fromDate, String toDate, int numGuests) {
        this.searchQuery = searchQuery;
        filteredHotels = new HotelList();
        this.numGuests = numGuests;
        this.fromDate = fromDate;
        this.toDate = toDate;
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_hotel_list, container, false);
        findViews(v);
        initViews();
        loadHotelData();
        return v;
    }

    private void findViews(View v) {
        fragment_LST_hotels = v.findViewById(R.id.fragment_LST_hotels);
        loading_indicator_hotel_view = v.findViewById(R.id.loading_indicator_hotel_view);
    }

    private void initViews() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        fragment_LST_hotels.setLayoutManager(gridLayoutManager);

    }


    //load hotels and favorite hotels as well
    private void loadHotelData() {

        loading_indicator_hotel_view.setVisibility(View.VISIBLE);
        DataManager.getInstance().setOnDataLoadHotel(new OnDataLoadHotel() {
            @Override
            public void dataLoaded(HotelList hotelList) {
                filterHotelSearch(hotelList);
                DataManager.getInstance().setOnDataLoadFavorite(new OnDataLoadFavorite() {
                    @Override
                    public void onFavoriteDataLoad(ArrayList<String> favoriteHotelIds) {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hotelAdapter = new HotelAdapter(requireContext(), filteredHotels.getHotelList(), favoriteHotelIds);
                                hotelAdapter.setFromDate(fromDate);
                                hotelAdapter.setToDate(toDate);
                                hotelAdapter.setNumGuests(numGuests);
                                fragment_LST_hotels.setAdapter(hotelAdapter);
                                loading_indicator_hotel_view.setVisibility(View.GONE);
                            }
                        });
                    }
                });

                DataManager.getInstance().loadFavoriteHotels(user.getUid());
            }
        });

        DataManager.getInstance().loadHotelListDB();
    }

    private void filterHotelSearch(HotelList hotelList) {
        if (hotelList == null)
            return;

        if (searchQuery == null || searchQuery.isEmpty()) {
            filteredHotels = hotelList;
            return;
        }

        Hotel hotelCheck;
        for (int i = 0; i < hotelList.getHotelList().size(); i++) {
            hotelCheck = hotelList.getHotelList().get(i);
            if (hotelCheck.getHotel_name().toLowerCase().contains(searchQuery.toLowerCase()) || hotelCheck.getCountry().toLowerCase().contains(searchQuery.toLowerCase())
                    || hotelCheck.getCity().toLowerCase().contains(searchQuery.toLowerCase())) {
                filteredHotels.addHotel(hotelCheck);
            }
        }

    }
}