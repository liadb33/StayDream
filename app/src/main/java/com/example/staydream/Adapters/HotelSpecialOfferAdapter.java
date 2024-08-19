package com.example.staydream.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.staydream.Activities.AboutHotelActivity;
import com.example.staydream.Data.DataManager;
import com.example.staydream.Interface.SpecialOfferDetailsCallback;
import com.example.staydream.Models.Hotel;
import com.example.staydream.R;
import com.example.staydream.Utilities.ImageLoader;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class HotelSpecialOfferAdapter extends RecyclerView.Adapter<HotelSpecialOfferAdapter.SpecialHotelViewHolder> {


    private ArrayList<Hotel> specialHotels;
    private ArrayList<String> favoriteHotels;
    private boolean isFavorite;
    private Context context;
    private SpecialOfferDetailsCallback specialOfferDetailsCallback;
    private FirebaseUser user;


    public HotelSpecialOfferAdapter(Context context, ArrayList<Hotel> specialHotels,ArrayList<String> favoriteHotels) {
        this.context = context;
        this.specialHotels = specialHotels;
        this.favoriteHotels = favoriteHotels;
        isFavorite = false;
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    @NonNull
    @Override
    public SpecialHotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.special_hotel_item, parent, false);
        return new SpecialHotelViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SpecialHotelViewHolder holder, int position) {

        Hotel hotel = specialHotels.get(position);

        if (favoriteHotels.contains(String.valueOf(hotel.getHotel_id())))
            holder.special_hotel_favorite.setImageResource(R.drawable.filled_white_heart);
        else
            holder.special_hotel_favorite.setImageResource(R.drawable.outline_white_heart);

        ImageLoader.getInstance().load(hotel.getPhoto1(), holder.special_hotel_IMG);
        holder.special_hotel_name.setText(hotel.getHotel_name());
        holder.special_hotel_location.setText(String.format("%s, %s", hotel.getCity(), hotel.getCountry()));
        holder.special_CARD_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                specialOfferDetailsCallback.checkValidDetails(hotel);
            }
        });
        holder.special_hotel_price.setText(hotel.getPrice() + "$/Night");
        holder.special_hotel_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFavorite = !isFavorite;
                if (!isFavorite)
                    holder.special_hotel_favorite.setImageResource(R.drawable.filled_white_heart);
                else
                    holder.special_hotel_favorite.setImageResource(R.drawable.outline_white_heart);
                DataManager.getInstance().saveFavoriteDB(user.getUid(), hotel.getHotel_id());
            }
        });
    }



    private Hotel getItem(int position) {
        return specialHotels.get(position);
    }

    @Override
    public int getItemCount() {
        // Check if hotelList is null before accessing its size
        if (specialHotels == null) {
            return 0;
        }
        return Math.min(specialHotels.size(), 10);
    }

    public class SpecialHotelViewHolder extends RecyclerView.ViewHolder {

        private final ShapeableImageView special_hotel_IMG;
        private final MaterialTextView special_discount;
        private final AppCompatImageView special_hotel_favorite;
        private final MaterialTextView special_hotel_name;
        private final MaterialTextView special_hotel_location;
        private final MaterialTextView special_hotel_price;
        private final MaterialCardView special_CARD_hotel;

        public SpecialHotelViewHolder(@NonNull View itemView) {
            super(itemView);

            special_hotel_IMG = itemView.findViewById(R.id.special_hotel_IMG);
            special_discount = itemView.findViewById(R.id.special_discount);
            special_hotel_favorite = itemView.findViewById(R.id.special_hotel_favorite);
            special_hotel_name = itemView.findViewById(R.id.special_hotel_name);
            special_hotel_location = itemView.findViewById(R.id.special_hotel_location);
            special_hotel_price = itemView.findViewById(R.id.special_hotel_price);
            special_CARD_hotel = itemView.findViewById(R.id.special_CARD_hotel);
        }
    }

    public void setSpecialOfferDetailsCallback(SpecialOfferDetailsCallback specialOfferDetailsCallback) {
        this.specialOfferDetailsCallback = specialOfferDetailsCallback;
    }
}
