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

public class OverviewFragment extends Fragment {

    private MaterialTextView frag_overview_hotel;
    private Hotel hotel;

    public OverviewFragment() {
        // Required empty public constructor
    }

    public OverviewFragment(Hotel hotel) {
       this.hotel = hotel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_overview, container, false);
        findViews(v);
        initViews(v);
        return v;
    }

    private void initViews(View v) {
        frag_overview_hotel.setText(hotel.getOverview());
    }

    private void findViews(View v) {
        frag_overview_hotel = v.findViewById(R.id.frag_overview_hotel);
    }


}