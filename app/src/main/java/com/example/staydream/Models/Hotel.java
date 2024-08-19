package com.example.staydream.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.staydream.Activities.MainActivity;
import com.example.staydream.Utilities.LocationManager;

import java.util.Arrays;
import java.util.List;

public class Hotel implements Parcelable {

   private int hotel_id;
   private String hotel_name;
   private String city;
   private String addressline1;
   private String country;
   private double longitude;
   private double latitude;
   private String url;
   private String checkin = "03:00 PM";
   private String checkout;
   private int numberrooms;
   private int numberfloors;
   private int yearopened;
   private String photo1;
   private String photo2;
   private String photo3;
   private  String photo4;
   private  String photo5;
   private String continent_name;
   private int number_of_reviews;
   private double rating_average;
   private String overview;
   private int price;


   public Hotel(){

   }



    protected Hotel(Parcel in) {
        hotel_name = in.readString();
        city = in.readString();
        addressline1 = in.readString();
        country = in.readString();
        longitude = in.readDouble();
        latitude = in.readDouble();
        url = in.readString();
        checkin = in.readString();
        checkout = in.readString();
        numberrooms = in.readInt();
        numberfloors = in.readInt();
        yearopened = in.readInt();
        photo1 = in.readString();
        photo2 = in.readString();
        photo3 = in.readString();
        photo4 = in.readString();
        photo5 = in.readString();
        continent_name = in.readString();
        number_of_reviews = in.readInt();
        rating_average = in.readDouble();
        overview = in.readString();
        hotel_id = in.readInt();
        price = in.readInt();
    }

    public static final Creator<Hotel> CREATOR = new Creator<Hotel>() {
        @Override
        public Hotel createFromParcel(Parcel in) {
            return new Hotel(in);
        }

        @Override
        public Hotel[] newArray(int size) {
            return new Hotel[size];
        }
    };

    public List<String> getImageList() {
        return Arrays.asList(photo1, photo2, photo3, photo4, photo5);
    }

    public String getCity() {
        return city;
    }

    public Hotel setCity(String city) {
        this.city = city;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public Hotel setPrice(int price) {
        this.price = price;
        return this;
    }

    public String getOverview() {
        return overview;
    }

    public Hotel setOverview(String overview) {
        this.overview = overview;
        return this;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public Hotel setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
        return this;
    }

    public String getAddressline1() {
        return addressline1;
    }

    public Hotel setAddressline1(String addressline1) {
        this.addressline1 = addressline1;
        return this;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public Hotel setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Hotel setCountry(String country) {
        this.country = country;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public Hotel setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public Hotel setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Hotel setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getCheckin() {
        return checkin;
    }

    public Hotel setCheckin(String checkin) {
        this.checkin = checkin;
        return this;
    }

    public String getCheckout() {
        return checkout;
    }

    public Hotel setCheckout(String checkout) {
        this.checkout = checkout;
        return this;
    }

    public int getNumberrooms() {
        return numberrooms;
    }

    public Hotel setNumberrooms(int numberrooms) {
        this.numberrooms = numberrooms;
        return this;
    }

    public int getNumberfloors() {
        return numberfloors;
    }

    public Hotel setNumberfloors(int numberfloors) {
        this.numberfloors = numberfloors;
        return this;
    }

    public int getYearopened() {
        return yearopened;
    }

    public Hotel setYearopened(int yearopened) {
        this.yearopened = yearopened;
        return this;
    }

    public String getPhoto1() {
        return photo1;
    }

    public Hotel setPhoto1(String photo1) {
        this.photo1 = photo1;
        return this;
    }

    public String getPhoto2() {
        return photo2;
    }

    public Hotel setPhoto2(String photo2) {
        this.photo2 = photo2;
        return this;
    }

    public String getPhoto3() {
        return photo3;
    }

    public Hotel setPhoto3(String photo3) {
        this.photo3 = photo3;
        return this;
    }

    public String getPhoto4() {
        return photo4;
    }

    public Hotel setPhoto4(String photo4) {
        this.photo4 = photo4;
        return this;
    }

    public String getPhoto5() {
        return photo5;
    }

    public Hotel setPhoto5(String photo5) {
        this.photo5 = photo5;
        return this;
    }

    public String getContinent_name() {
        return continent_name;
    }

    public Hotel setContinent_name(String continent_name) {
        this.continent_name = continent_name;
        return this;
    }

    public int getNumber_of_reviews() {
        return number_of_reviews;
    }

    public Hotel setNumber_of_reviews(int number_of_reviews) {
        this.number_of_reviews = number_of_reviews;
        return this;
    }

    public double getRating_average() {
        return rating_average;
    }

    public Hotel setRating_average(double rating_average) {
        this.rating_average = rating_average;
        return this;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "hotel_name='" + hotel_name + '\'' +
                ", city='" + city + '\'' +
                ", addressline1='" + addressline1 + '\'' +
                ", country='" + country + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", url='" + url + '\'' +
                ", checkin='" + checkin + '\'' +
                ", checkout='" + checkout + '\'' +
                ", numberrooms=" + numberrooms +
                ", numberfloors=" + numberfloors +
                ", yearopened=" + yearopened +
                ", photo1='" + photo1 + '\'' +
                ", continent_name='" + continent_name + '\'' +
                ", number_of_reviews=" + number_of_reviews +
                ", rating_average=" + rating_average +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(hotel_name);
        dest.writeString(city);
        dest.writeString(addressline1);
        dest.writeString(country);
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
        dest.writeString(url);
        dest.writeString(checkin);
        dest.writeString(checkout);
        dest.writeInt(numberrooms);
        dest.writeInt(numberfloors);
        dest.writeInt(yearopened);
        dest.writeString(photo1);
        dest.writeString(photo2);
        dest.writeString(photo3);
        dest.writeString(photo4);
        dest.writeString(photo5);
        dest.writeString(continent_name);
        dest.writeInt(number_of_reviews);
        dest.writeDouble(rating_average);
        dest.writeString(overview);
        dest.writeInt(hotel_id);
        dest.writeInt(price);
    }
}