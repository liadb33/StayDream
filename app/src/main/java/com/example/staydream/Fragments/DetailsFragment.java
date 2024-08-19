package com.example.staydream.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.staydream.Models.Hotel;
import com.example.staydream.R;
import com.google.android.material.textview.MaterialTextView;

public class DetailsFragment extends Fragment {

    private Hotel hotel;
    private MaterialTextView check_in_time_details;
    private MaterialTextView check_out_time_details;
    private MaterialTextView address_details;
    private MaterialTextView hotel_site_url_details;
    private MaterialTextView number_of_floors_details;
    private MaterialTextView opened_in_details;
    private MaterialTextView continent_details;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public DetailsFragment(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_details, container, false);

        findViews(v);
        initViews();
        return v;
    }

    private void initViews() {
        check_in_time_details.setText(hotel.getCheckin());
        check_out_time_details.setText(hotel.getCheckout());
        address_details.setText(hotel.getAddressline1());
        hotel_site_url_details.setText(hotel.getUrl());
        number_of_floors_details.setText(String.valueOf(hotel.getNumberfloors()));
        opened_in_details.setText(String.valueOf(hotel.getYearopened()));
        continent_details.setText(hotel.getContinent_name());
    }

    private void findViews(View view) {
        check_in_time_details = view.findViewById(R.id.check_in_time_details);
        check_out_time_details = view.findViewById(R.id.check_out_time_details);
        address_details = view.findViewById(R.id.address_details);
        hotel_site_url_details = view.findViewById(R.id.hotel_site_url_details);
        number_of_floors_details = view.findViewById(R.id.number_of_floors_details);
        opened_in_details = view.findViewById(R.id.opened_in_details);
        continent_details = view.findViewById(R.id.continent_details);
    }
}