package com.example.staydream.Models;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Reservation {

    private static final int DEFAULT_ID = 1000;
    private static final String CONFIRMED = "CONFIRMED";
    private static final String CANCELLED = "CANCELLED";
    private static int reserveOccurences;
    private int reservationId;
    private int hotelId;
    private String hotelPhoto;
    private String hotelName;
    private Date checkInDate;
    private Date checkOutDate;
    private int numberOfGuests;
    private int numberOfRooms;
    private double totalPrice;
    private String specialRequests;
    private String reservationStatus;
    private String reservationDate;

    public Reservation(){

    }

    public Reservation(int hotelId, String hotelName, Date checkInDate, Date checkOutDate, int numberOfGuests, int numberOfRooms, double totalPrice, String specialRequests,String hotelPhoto) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfGuests = numberOfGuests;
        this.numberOfRooms = numberOfRooms;
        this.totalPrice = totalPrice;
        this.specialRequests = specialRequests;
        this.hotelPhoto = hotelPhoto;

        Date currentTime = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd HH:mm", Locale.getDefault());
        reservationDate = sdf.format(currentTime);

        reservationStatus = CONFIRMED;
        reservationId = DEFAULT_ID + reserveOccurences;
        reserveOccurences++;
    }

    public void cancelReservation(){
        reservationStatus = CANCELLED;
    }

    public static int getReserveOccurences() {
        return reserveOccurences;
    }

    public static void setReserveOccurences(int reserveOccurences) {
        Reservation.reserveOccurences = reserveOccurences;
    }

    public int getReservationId() {
        return reservationId;
    }

    public Reservation setReservationId(int reservationId) {
        this.reservationId = reservationId;
        return this;
    }

    public String getHotelPhoto() {
        return hotelPhoto;
    }

    public Reservation setHotelPhoto(String hotelPhoto) {
        this.hotelPhoto = hotelPhoto;
        return this;
    }

    public int getHotelId() {
        return hotelId;
    }

    public Reservation setHotelId(int hotelId) {
        this.hotelId = hotelId;
        return this;
    }

    public String getHotelName() {
        return hotelName;
    }

    public Reservation setHotelName(String hotelName) {
        this.hotelName = hotelName;
        return this;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Reservation setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
        return this;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public Reservation setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
        return this;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public Reservation setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
        return this;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public Reservation setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
        return this;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Reservation setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public Reservation setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
        return this;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }

    public Reservation setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
        return this;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public Reservation setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
        return this;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", hotelId=" + hotelId +
                ", hotelPhoto='" + hotelPhoto + '\'' +
                ", hotelName='" + hotelName + '\'' +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", numberOfGuests=" + numberOfGuests +
                ", numberOfRooms=" + numberOfRooms +
                ", totalPrice=" + totalPrice +
                ", specialRequests='" + specialRequests + '\'' +
                ", reservationStatus='" + reservationStatus + '\'' +
                ", reservationDate='" + reservationDate + '\'' +
                '}';
    }
}
