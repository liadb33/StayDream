package com.example.staydream.Data;

import com.example.staydream.Models.Reservation;

import java.util.ArrayList;

public class ReservationManager {

    private ArrayList<Reservation> reservationsList;

    public ReservationManager(){
        reservationsList = new ArrayList<Reservation>();
    }

    public ReservationManager(ArrayList<Reservation> reservationArrayList) {
        this.reservationsList = reservationArrayList;
    }

    public void addReservation(Reservation reservation){
        reservationsList.add(reservation);
    }

    public ArrayList<Reservation> getReservationsList() {
        return reservationsList;
    }

    public ReservationManager setReservationsList(ArrayList<Reservation> reservationsList) {
        this.reservationsList = reservationsList;
        return this;
    }


}
