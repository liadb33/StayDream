package com.example.staydream.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.staydream.Activities.AboutHotelActivity;
import com.example.staydream.Models.Hotel;
import com.example.staydream.R;
import com.example.staydream.Utilities.ImageLoader;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    private static final int NUM_PHOTOS = 5;
    private final ArrayList<Hotel> hotels;
    private Context context;

    public HotelAdapter(Context context,ArrayList<Hotel> hotels) {
        this.hotels = hotels;
        this.context = context;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_item,parent,false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {

        Hotel hotel = getItem(position);

        ImageLoader.getInstance().load(hotel.getPhoto1(),holder.hotel_SHP_IMG);
        holder.hotel_TXT_name.setText(hotel.getHotel_name());
        holder.hotel_TXT_location.setText(String.format("%s, %s", hotel.getCity(), hotel.getCountry()));
        holder.hotel_CARD_data.setOnClickListener(v -> changeActivityAbout(hotel));
    }

    private void changeActivityAbout(Hotel hotel){
        Intent intent = new Intent(context,AboutHotelActivity.class);
        intent.putExtra(AboutHotelActivity.KEY_LATITUDE,hotel.getLatitude());
        intent.putExtra(AboutHotelActivity.KEY_LONGITUDE,hotel.getLongitude());
        intent.putExtra(AboutHotelActivity.KEY_RATING,hotel.getRating_average());
        intent.putExtra(AboutHotelActivity.KEY_NAME,hotel.getHotel_name());
        intent.putExtra(AboutHotelActivity.KEY_LOCATION,hotel.getCountry() +", " + hotel.getCity());
        intent.putExtra(AboutHotelActivity.KEY_PHOTOS,hotel.getImageArr());
        context.startActivity(intent);
    }

    private Hotel getItem(int position) {
        return hotels.get(position);
    }

    @Override
    public int getItemCount() {
        return hotels == null ? 0 : hotels.size();
    }

    public class HotelViewHolder extends RecyclerView.ViewHolder {

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

        }
    }
}
