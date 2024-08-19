package com.example.staydream.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;

public class FavoriteHotelFragment extends Fragment {

    private RecyclerView favorite_hotel_recycle;
    private HotelAdapter hotelAdapter;
    private CircularProgressIndicator loading_indicator_favorite_hotel;
    private FirebaseUser user;
    private String fromDate;
    private String toDate;
    private int numGuests;

    public FavoriteHotelFragment() {

    }

    public FavoriteHotelFragment(String fromDate,String toDate,int numGuests){
        this.numGuests = numGuests;
        this.fromDate = fromDate;
        this.toDate = toDate;
        user = FirebaseAuth.getInstance().getCurrentUser();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_favorite_hotel, container, false);

        findViews(view);
        initViews();
        loadFavoriteHotels();
        return view;
    }


    private void initViews() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        favorite_hotel_recycle.setLayoutManager(gridLayoutManager);
    }

    private void findViews(View view) {
        favorite_hotel_recycle = view.findViewById(R.id.favorite_hotel_recycle);
        loading_indicator_favorite_hotel = view.findViewById(R.id.loading_indicator_favorite_hotel);
    }

    private void loadFavoriteHotels() {
        loading_indicator_favorite_hotel.setVisibility(View.VISIBLE);
        DataManager.getInstance().setOnDataLoadHotel(new OnDataLoadHotel() {
            @Override
            public void dataLoaded(HotelList hotelList) {
                DataManager.getInstance().setOnDataLoadFavorite(new OnDataLoadFavorite() {
                    @Override
                    public void onFavoriteDataLoad(ArrayList<String> favoriteHotelIds) {
                        // Filter the hotel list to include only favorite hotels
                        ArrayList<Hotel> favoriteHotels = new ArrayList<>();
                        for (Hotel hotel : hotelList.getHotelList()) {
                            if (favoriteHotelIds.contains(String.valueOf(hotel.getHotel_id()))) {
                                favoriteHotels.add(hotel);
                            }
                        }
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hotelAdapter = new HotelAdapter(requireContext(),favoriteHotels, favoriteHotelIds);
                                hotelAdapter.setFromDate(fromDate);
                                hotelAdapter.setToDate(toDate);
                                hotelAdapter.setNumGuests(numGuests);
                                favorite_hotel_recycle.setAdapter(hotelAdapter);
                                loading_indicator_favorite_hotel.setVisibility(View.GONE);
                            }
                        });
                    }
                });

                DataManager.getInstance().loadFavoriteHotels(user.getUid());
            }
        });

        DataManager.getInstance().loadHotelListDB();
    }
}