package com.example.staydream.Interface;

import com.example.staydream.Models.Reservation;

import java.util.ArrayList;

public interface OnDataLoadReservationUser {

    void onReservationsLoaded(ArrayList<Reservation> reservations);
}
