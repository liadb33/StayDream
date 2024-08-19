package com.example.staydream.Data;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.staydream.Interface.OnDataLoadFavorite;
import com.example.staydream.Interface.OnDataLoadReservationUser;
import com.example.staydream.Interface.OnDataLoadReview;
import com.example.staydream.Models.Hotel;
import com.example.staydream.Models.HotelList;
import com.example.staydream.Interface.OnDataLoadHotel;
import com.example.staydream.Models.Reservation;
import com.example.staydream.Models.UserReview;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

public class DataManager {

    private HotelList hotelList;
    private static volatile DataManager instance = null;
    private OnDataLoadHotel onDataLoadHotel;

    private OnDataLoadFavorite onDataLoadFavorite;

    private OnDataLoadReservationUser onDataLoadReservation;

    private OnDataLoadReview onReviewsLoaded;


    public DataManager() {
        this.hotelList = new HotelList();
    }

    public void setOnDataLoadHotel(OnDataLoadHotel onDataLoadHotel) {
        this.onDataLoadHotel = onDataLoadHotel;
    }

    public void setOnDataLoadReservation(OnDataLoadReservationUser onDataLoadReservation) {
        this.onDataLoadReservation = onDataLoadReservation;
    }

    public void setOnDataLoadFavorite(OnDataLoadFavorite onDataLoadFavorite) {
        this.onDataLoadFavorite = onDataLoadFavorite;
    }

    public void setOnReviewsLoaded(OnDataLoadReview onReviewsLoaded) {
        this.onReviewsLoaded = onReviewsLoaded;
    }

    //save Hotels to DB
    public void saveHotelListDB(HotelList hotelList) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Hotels");
        myRef.setValue(hotelList.getHotelList());
    }

    //loads up the reservation the specific user made
    public void loadReservationFromUserDB(String userId) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reservationsRef = database.getReference("Reservations").child(userId);

        reservationsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Reservation> reservationsList = new ArrayList<>();
                for (DataSnapshot reservationSnapshot : snapshot.getChildren()) {
                    Reservation reservation = reservationSnapshot.getValue(Reservation.class);
                    if (reservation != null)
                        reservationsList.add(reservation);
                }
                onDataLoadReservation.onReservationsLoaded(reservationsList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //pass
            }
        });
    }


    //saves a new reservation under the userId that's received
    public void saveReservationToDB(String userId, Reservation reservation) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reservationsRef = database.getReference("Reservations");

        String reservationId = String.valueOf(reservation.getReservationId());
        DatabaseReference userReservationsRef = reservationsRef.child(userId).child(reservationId);
        userReservationsRef.setValue(reservation);
    }

    //saves a new review under the hotel's id
    public void saveReviewToDB(int hotelId, UserReview userReview) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reviewsRef = database.getReference("Reviews");

        String reviewKey = reviewsRef.child(String.valueOf(hotelId)).push().getKey();
        reviewsRef.child(String.valueOf(hotelId)).child(reviewKey).setValue(userReview);
    }

    //loads reviews from DB
    public void loadReviewsFromDB(int hotelId) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reviewsRef = database.getReference("Reviews").child(String.valueOf(hotelId));

        reviewsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<UserReview> reviews = new ArrayList<>();
                for (DataSnapshot reviewSnapshot : dataSnapshot.getChildren()) {
                    UserReview review = reviewSnapshot.getValue(UserReview.class);
                    if (review != null) {
                        reviews.add(review);
                    }
                }
                if (onReviewsLoaded != null)
                    onReviewsLoaded.onDataLoadReview(reviews);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //pass
            }
        });
    }

    //saves the favorite hotel's Id under the user's id
    public void saveFavoriteDB(String userId, int hotelId) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reservationsRef = database.getReference("Favorites");

        DatabaseReference userReservationsRef = reservationsRef.child(userId).child(String.valueOf(hotelId));
        userReservationsRef.setValue(hotelId);
    }

    //loads from DB the favorite hotels of the specific user
    public void loadFavoriteHotels(String userId) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference favoritesRef = database.getReference("Favorites").child(userId);

        favoritesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> favoriteHotelIds = new ArrayList<>();
                for (DataSnapshot hotelSnapshot : snapshot.getChildren()) {
                    favoriteHotelIds.add(hotelSnapshot.getKey());
                }
                onDataLoadFavorite.onFavoriteDataLoad(favoriteHotelIds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    //load hotels from Json
    public HotelList loadHotelsFromJson(Context context, String fileName) {
        try {
            // Use AssetManager to open the file
            InputStream inputStream = context.getAssets().open(fileName);
            Reader reader = new InputStreamReader(inputStream);

            Gson gson = new Gson();
            HotelList hotelList = gson.fromJson(reader, HotelList.class);

            // Ensure the hotel list is not null
            if (hotelList.getHotelList() == null) {
                hotelList.setHotelList(new ArrayList<>());
            }
            hotelList.addHotelPrice();
            return hotelList;
        } catch (Exception e) {
            e.printStackTrace();
            return new HotelList();
        }
    }


    //loads the hotels from DB
    public void loadHotelListDB() {
        DatabaseReference hotelRef = FirebaseDatabase.getInstance().getReference("Hotels");
        hotelRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hotelList = new HotelList();
                for (DataSnapshot hotelSnapshot : snapshot.getChildren()) {
                    Hotel hotel = hotelSnapshot.getValue(Hotel.class);
                    if (hotel != null)
                        hotelList.addHotel(hotel);
                }
                onDataLoadHotel.dataLoaded(hotelList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //pass
            }
        });
    }

    public static DataManager init(Context context) {
        if (instance == null) {
            synchronized (DataManager.class) {
                if (instance == null)
                    instance = new DataManager();
            }
        }
        return getInstance();
    }

    public static DataManager getInstance() {
        return instance;
    }

}
