package com.example.staydream.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.staydream.Adapters.ReservationAdapter;
import com.example.staydream.Data.DataManager;
import com.example.staydream.Interface.OnDataLoadReservationUser;
import com.example.staydream.Models.Reservation;
import com.example.staydream.R;
import com.example.staydream.Utilities.SignalManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ReservationListFragment extends Fragment {


    private ReservationAdapter reservationAdapter;
    private RecyclerView fragment_LST_reservations;

    private ArrayList<Reservation> confirmedReservations;
    public ReservationListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reservation, container, false);

        findViews(view);
        initViews();
        loadReservationData();
        return view;
    }

    private void initViews() {
        confirmedReservations = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        fragment_LST_reservations.setLayoutManager(linearLayoutManager);
    }

    private void findViews(View view) {
        fragment_LST_reservations = view.findViewById(R.id.fragment_LST_reservations);
    }


    //loads from the DB only the confirmed reservations, the cancelled reservation arent shown
    private void loadReservationData(){
        DataManager.getInstance().setOnDataLoadReservation(new OnDataLoadReservationUser() {
            @Override
            public void onReservationsLoaded(ArrayList<Reservation> reservations) {
                for (int i = 0; i < reservations.size(); i++) {
                    if(reservations.get(i).getReservationStatus().equals("CONFIRMED"))
                        confirmedReservations.add(reservations.get(i));
                }
                requireActivity().runOnUiThread(() -> {
                    reservationAdapter = new ReservationAdapter(confirmedReservations);
                    fragment_LST_reservations.setAdapter(reservationAdapter);
                });
            }
        });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null)
            DataManager.getInstance().loadReservationFromUserDB(user.getUid());
        else
            SignalManager.getInstance().toast("User Not Found");
    }
}