package com.example.staydream.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.staydream.Data.DataManager;
import com.example.staydream.Models.Reservation;
import com.example.staydream.R;
import com.example.staydream.Utilities.ImageLoader;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {

    private FirebaseUser user;
    private final String CANCELLED = "CANCELLED";
    private SimpleDateFormat dateFormat;
    private final ArrayList<Reservation> reservationsList;

    public ReservationAdapter(ArrayList<Reservation> reservationsList) {
        this.reservationsList = reservationsList;
        user = FirebaseAuth.getInstance().getCurrentUser();
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_item,parent,false);
        return new ReservationViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        Reservation reservation = getItem(position);

        ImageLoader.getInstance().load(reservation.getHotelPhoto(),holder.reservation_IMG);
        holder.reservation_LBL_name.setText(reservation.getHotelName());
        String formattedCheckInDate = dateFormat.format(reservation.getCheckInDate());
        holder.reservation_LBL_checkin.setText("Check-In: " + formattedCheckInDate);
        String formattedCheckOutDate = dateFormat.format(reservation.getCheckOutDate());
        holder.reservation_LBL_checkout.setText("Check-Out: " + formattedCheckOutDate);
        holder.reservation_guest_num_rooms.setText(reservation.getNumberOfGuests() + " Guests, " + reservation.getNumberOfRooms() + " Rooms");
        holder.reservation_date.setText("Date Reserved: " + reservation.getReservationDate());
        holder.reservation_special_TXT.setText("Special Req: " + reservation.getSpecialRequests());
        holder.reservation_price.setText("Total Price: $" + reservation.getTotalPrice());

        holder.reservation_BOOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getBindingAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    reservation.setReservationStatus(CANCELLED);
                    DataManager.getInstance().saveReservationToDB(user.getUid(),reservation);
                    reservationsList.remove(currentPosition);
                    notifyItemChanged(currentPosition);
                    notifyItemRangeChanged(currentPosition,reservationsList.size());
                }
            }
        });
    }

    private Reservation getItem(int position){
        return reservationsList.get(position);
    }

    @Override
    public int getItemCount() {
        return reservationsList == null ? 0 : reservationsList.size();
    }

    public class ReservationViewHolder extends RecyclerView.ViewHolder {

        private final ShapeableImageView reservation_IMG;
        private final MaterialTextView reservation_LBL_name;
        private final MaterialTextView reservation_LBL_checkin;
        private final MaterialTextView reservation_LBL_checkout;
        private final MaterialTextView reservation_guest_num_rooms;
        private final MaterialTextView reservation_date;
        private final MaterialTextView reservation_special_TXT;
        private final MaterialTextView reservation_price;

        private final ExtendedFloatingActionButton reservation_BOOK;

        public ReservationViewHolder(@NonNull View itemView) {
            super(itemView);


            reservation_IMG = itemView.findViewById(R.id.reservation_IMG);
            reservation_LBL_name = itemView.findViewById(R.id.reservation_LBL_name);
            reservation_LBL_checkin = itemView.findViewById(R.id.reservation_LBL_checkin);
            reservation_LBL_checkout = itemView.findViewById(R.id.reservation_LBL_checkout);
            reservation_guest_num_rooms = itemView.findViewById(R.id.reservation_guest_num_rooms);
            reservation_date = itemView.findViewById(R.id.reservation_date);
            reservation_special_TXT = itemView.findViewById(R.id.reservation_special_TXT);
            reservation_price = itemView.findViewById(R.id.reservation_price);
            reservation_BOOK = itemView.findViewById(R.id.reservation_BOOK);
        }
    }

}
