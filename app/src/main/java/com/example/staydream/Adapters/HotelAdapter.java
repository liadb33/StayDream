package com.example.staydream.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.staydream.Activities.AboutHotelActivity;
import com.example.staydream.Data.DataManager;
import com.example.staydream.Models.Hotel;
import com.example.staydream.R;
import com.example.staydream.Utilities.ImageLoader;
import com.example.staydream.Utilities.SignalManager;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    private String fromDate;
    private String toDate;

    private FirebaseUser user;
    private int numGuests;
    private static final int NUM_PHOTOS = 5;
    private final ArrayList<Hotel> hotels;
    private boolean isFavorite;
    private Context context;
    private ArrayList<String> favoriteHotels;


    public HotelAdapter(Context context, ArrayList<Hotel> hotels, ArrayList<String> favoriteHotels) {
        this.hotels = hotels;
        this.context = context;
        isFavorite = false;
        user = FirebaseAuth.getInstance().getCurrentUser();
        this.favoriteHotels = favoriteHotels;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_item, parent, false);
        return new HotelViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {

        Hotel hotel = getItem(position);

        if (favoriteHotels.contains(String.valueOf(hotel.getHotel_id())))
            holder.hotel_IMG_favorite.setImageResource(R.drawable.filled_white_heart);
        else
            holder.hotel_IMG_favorite.setImageResource(R.drawable.outline_white_heart);

        ImageLoader.getInstance().load(hotel.getPhoto1(), holder.hotel_SHP_IMG);
        holder.hotel_TXT_name.setText(hotel.getHotel_name());
        holder.hotel_TXT_location.setText(String.format("%s, %s", hotel.getCity(), hotel.getCountry()));
        holder.hotel_CARD_data.setOnClickListener(v -> changeActivityAbout(hotel));
        holder.hotel_TXT_price.setText(hotel.getPrice() + "$/Night");
        holder.hotel_IMG_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFavorite = !isFavorite;
                if (!isFavorite)
                    holder.hotel_IMG_favorite.setImageResource(R.drawable.filled_white_heart);
                else
                    holder.hotel_IMG_favorite.setImageResource(R.drawable.outline_white_heart);
                DataManager.getInstance().saveFavoriteDB(user.getUid(), hotel.getHotel_id());
            }
        });
    }


    private void changeActivityAbout(Hotel hotel) {
        if(fromDate == null || toDate == null || numGuests == 0)
            SignalManager.getInstance().toast("One of the input details isn't filled\n Fill them in the main page");
        else{
            Intent intent = new Intent(context, AboutHotelActivity.class);
            intent.putExtra(AboutHotelActivity.KEY_HOTEL, hotel);
            intent.putExtra(AboutHotelActivity.KEY_FROM_DATE, fromDate);
            intent.putExtra(AboutHotelActivity.KEY_TO_DATE, toDate);
            intent.putExtra(AboutHotelActivity.KEY_GUESTS, numGuests);
            context.startActivity(intent);
        }
    }

    private Hotel getItem(int position) {
        return hotels.get(position);
    }

    @Override
    public int getItemCount() {
        return hotels == null ? 0 : hotels.size();
    }

    public void setNumGuests(int numGuests) {
        this.numGuests = numGuests;
    }

    public class HotelViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatImageView hotel_IMG_favorite;
        private final MaterialCardView hotel_CARD_data;
        private final ShapeableImageView hotel_SHP_IMG;
        private final MaterialTextView hotel_TXT_name;
        private final MaterialTextView hotel_TXT_location;
        private final MaterialTextView hotel_TXT_price;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);

            hotel_SHP_IMG = itemView.findViewById(R.id.hotel_SHP_IMG);
            hotel_TXT_name = itemView.findViewById(R.id.hotel_TXT_name);
            hotel_TXT_location = itemView.findViewById(R.id.hotel_TXT_location);
            hotel_TXT_price = itemView.findViewById(R.id.hotel_TXT_price);
            hotel_CARD_data = itemView.findViewById(R.id.hotel_CARD_data);
            hotel_IMG_favorite = itemView.findViewById(R.id.hotel_IMG_favorite);
        }
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}
