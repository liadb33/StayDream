package com.example.staydream.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.staydream.Adapters.HotelAdapter;
import com.example.staydream.Adapters.HotelSpecialOfferAdapter;
import com.example.staydream.Data.DataManager;
import com.example.staydream.Interface.OnDataLoadFavorite;
import com.example.staydream.Interface.OnDataLoadHotel;
import com.example.staydream.Interface.SpecialOfferDetailsCallback;
import com.example.staydream.Models.Hotel;
import com.example.staydream.Models.HotelList;
import com.example.staydream.R;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Comparator;

public class SpecialOfferFragment extends Fragment {
    private ArrayList<Hotel> specialHotels;
    private RecyclerView special_offer_RECYCLER;
    private FirebaseUser user;
    private CircularProgressIndicator loading_indicator_special_offer;
    private SpecialOfferDetailsCallback specialOfferDetailsCallback;
    private HotelSpecialOfferAdapter hotelSpecialOfferAdapter;

    public SpecialOfferFragment() {
        // Required empty public constructor
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public SpecialOfferFragment(SpecialOfferDetailsCallback specialOfferDetailsCallback){
        this();
        this.specialOfferDetailsCallback = specialOfferDetailsCallback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_special_offer, container, false);

        findViews(view);
        initViews();
        loadAndSortHotels();
        return view;
    }

    private void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        special_offer_RECYCLER.setLayoutManager(linearLayoutManager);
    }

    private void findViews(View view) {
        special_offer_RECYCLER = view.findViewById(R.id.special_offer_RECYCLER);
        loading_indicator_special_offer = view.findViewById(R.id.loading_indicator_special_offer);
    }


    //loads 10 lowest hotels and favorites hotels among them as well
    private void loadAndSortHotels() {
        String userId = user.getUid();

        loading_indicator_special_offer.setVisibility(View.VISIBLE);
        DataManager.getInstance().setOnDataLoadHotel(new OnDataLoadHotel() {
            @Override
            public void dataLoaded(HotelList hotelList) {
                specialHotels = hotelList.getHotelList();
                specialHotels.sort(Comparator.comparingDouble(Hotel::getPrice));
                DataManager.getInstance().setOnDataLoadFavorite(new OnDataLoadFavorite() {
                    @Override
                    public void onFavoriteDataLoad(ArrayList<String> favoriteHotelIds) {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hotelSpecialOfferAdapter = new HotelSpecialOfferAdapter(requireContext(), specialHotels, favoriteHotelIds);
                                hotelSpecialOfferAdapter.setSpecialOfferDetailsCallback(specialOfferDetailsCallback);
                                special_offer_RECYCLER.setAdapter(hotelSpecialOfferAdapter);
                                loading_indicator_special_offer.setVisibility(View.GONE);
                            }
                        });
                    }
                });
                DataManager.getInstance().loadFavoriteHotels(userId);
            }
        });
        DataManager.getInstance().loadHotelListDB();
    }


}