package com.example.staydream.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.staydream.Interface.OnMapAttachCallback;
import com.example.staydream.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {

    private OnMapAttachCallback onMapAttachCallback;
    public MapFragment() {
        // Required empty public constructor
    }

    public MapFragment(OnMapAttachCallback onMapAttachCallback) {
        this.onMapAttachCallback = onMapAttachCallback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        FrameLayout touchInterceptor = new FrameLayout(requireContext()) {
            @Override
            public boolean dispatchTouchEvent(MotionEvent ev) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //disallow parent ScrollView to intercept touch events
                        getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        //allow parent ScrollView to intercept touch events
                        getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return super.dispatchTouchEvent(ev);
            }
        };

        touchInterceptor.addView(view);
        return touchInterceptor;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(onMapAttachCallback != null)
            onMapAttachCallback.onMapAttached();
    }

    public void onMapZoom(double latitude, double longitude) {
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                LatLng location = new LatLng(latitude, longitude);

                googleMap.clear();

                MarkerOptions markerOptions = new MarkerOptions()
                        .position(location)
                        .title(latitude + " : " + longitude);

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 13));

                googleMap.addMarker(markerOptions);

                // Optionally, you can keep the click listener for additional functionality
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        // Handle map click if needed
                    }
                });
            }
        });
    }
}