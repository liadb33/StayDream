package com.example.staydream.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.staydream.Data.DataManager;
import com.example.staydream.Models.Hotel;
import com.example.staydream.Models.Reservation;
import com.example.staydream.Models.UserReview;
import com.example.staydream.R;
import com.example.staydream.Data.ReservationManager;
import com.example.staydream.Utilities.SignalManager;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

public class ReserveHotelActivity extends AppCompatActivity {

    private static final double TAX = 0.17;
    public static final String KEY_FROM_DATE = "KEY_FROM_DATE";
    public static final String KEY_TO_DATE = "KEY_TO_DATE";
    public static final String KEY_HOTEL = "KEY_HOTEL";
    public static final String KEY_GUESTS = "KEY_GUESTS";
    private ExtendedFloatingActionButton reserve_confirm;
    private AutoCompleteTextView autoComplete_num_room;
    private TextInputEditText reserve_txt_input;
    private ExtendedFloatingActionButton apply_coupon_button_reserve;
    private TextInputEditText txt_coupon_code_input;
    private MaterialTextView original_price_reserve;
    private MaterialTextView discount_price_reserve;
    private MaterialTextView tax_price_reserve;
    private MaterialTextView total_price_reserve;
    private MaterialTextView reserve_hotel_city;
    private MaterialTextView check_in_and_out_reserve;
    private MaterialTextView num_guests_reserve;
    private MaterialTextView check_in_date_reserve;
    private MaterialTextView check_out_date_reserve;
    private MaterialTextView room_X_night_reserve;
    private TextInputEditText txt_user_review_input;
    private AppCompatImageButton back_button_reserve;
    private FirebaseUser user;
    private Reservation reservation;
    private ReservationManager reservationManager;
    private Hotel reserveHotel;
    private String specialRequest;
    private String fromDate;
    private String toDate;
    private String userReviewInput;
    private String couponInput;
    private double discountPrice;
    private double originalPrice;
    private double totalPrice;
    private int selectedGuestNum;
    private int selectedNumRoom = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        findViews();
        initViews();
    }

    @SuppressLint("SetTextI18n")
    private void initViews() {

        user = FirebaseAuth.getInstance().getCurrentUser();
        Intent previousIntent = getIntent();
        fromDate = previousIntent.getStringExtra(KEY_FROM_DATE);
        toDate = previousIntent.getStringExtra(KEY_TO_DATE);
        selectedGuestNum = previousIntent.getIntExtra(KEY_GUESTS, 0);
        reserveHotel = previousIntent.getParcelableExtra(KEY_HOTEL);
        //loadManagerFromDatabase();

        //sets options of num of rooms
        Integer[] numRoomOption = new Integer[]{1, 2, 3, 4, 5};
        ArrayAdapter<Integer> numRoomAdapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, numRoomOption);

        autoComplete_num_room.setAdapter(numRoomAdapter);

        //sets details by user
        reserve_hotel_city.setText(reserveHotel.getCity() + ", " + reserveHotel.getCountry());
        check_in_and_out_reserve.setText(reserveHotel.getCheckin() + " To " + reserveHotel.getCheckout());
        num_guests_reserve.setText(selectedGuestNum + " Guests");
        room_X_night_reserve.setText(selectedNumRoom + " Room X " + diffInDays() + " Night");
        check_in_date_reserve.setText(fromDate);
        check_out_date_reserve.setText(toDate);

        originalPrice = (diffInDays() * reserveHotel.getPrice()) * selectedNumRoom;
        apply_coupon_button_reserve.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                couponInput = txt_coupon_code_input.getText().toString();
                if (!couponInput.equals("ANDROIDSTUDIO")) {
                    SignalManager.getInstance().toast("Invalid Coupon");
                    discount_price_reserve.setText("$0.00");
                } else {
                    discountPrice = 0.25 * diffInDays() * originalPrice;
                    discount_price_reserve.setText("$" + discountPrice);
                    priceManagement();
                    SignalManager.getInstance().toast("Discount has been applied");
                }
            }
        });


        //defaults num of rooms to 1
        autoComplete_num_room.setText(String.valueOf(1), false);
        autoComplete_num_room.setDropDownBackgroundResource(R.color.white);
        autoComplete_num_room.setOnItemClickListener((parent, view, position, id) -> {
            selectedNumRoom = (int) parent.getItemAtPosition(position);
            room_X_night_reserve.setText(selectedNumRoom + " Room X " + diffInDays() + " Night");
            priceManagement();
        });

        back_button_reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReserveHotelActivity.this,AboutHotelActivity.class));
                finish();
            }
        });

        reserve_confirm.setOnClickListener(v -> confirmReserve());

        priceManagement();

    }

    //price algorithm
    @SuppressLint("SetTextI18n")
    private void priceManagement() {

        double tax = originalPrice * TAX;
        totalPrice = originalPrice + tax - discountPrice;
        String formattedPrice = String.format("%.2f", totalPrice);
        String formattedTax = String.format("%.2f", tax);

        original_price_reserve.setText("$" + originalPrice);
        tax_price_reserve.setText("$" + formattedTax);
        total_price_reserve.setText("$" + formattedPrice);
    }

    //set a new reservation
    private void confirmReserve() {

        userReviewInput = txt_user_review_input.getText().toString();
        specialRequest = reserve_txt_input.getText().toString();
        Date checkInDate = stringToDate(fromDate);
        Date checkOutDate = stringToDate(toDate);

        reservation = new Reservation(reserveHotel.getHotel_id(), reserveHotel.getHotel_name(), checkInDate, checkOutDate,
                selectedGuestNum, selectedNumRoom, totalPrice, specialRequest, reserveHotel.getPhoto1());

        SignalManager.getInstance().toast("BOOKING CONFIRMED!👌👌\n Returns To HomePage");
        saveAndtransactToMain();

    }

    private int diffInDays() {
        LocalDate fromDateStart = LocalDate.parse(fromDate);
        LocalDate toDateEnd = LocalDate.parse(toDate);
        return (int) ChronoUnit.DAYS.between(fromDateStart, toDateEnd);
    }

    private Date stringToDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    //saves reservation to DB and review, transacts to main.
    private void saveAndtransactToMain() {

        if (userReviewInput != null) {
            UserReview userReview = new UserReview(user.getDisplayName(), userReviewInput, user.getPhotoUrl().toString());
            DataManager.getInstance().saveReviewToDB(reserveHotel.getHotel_id(), userReview);
        }

        DataManager.getInstance().saveReservationToDB(user.getUid(), reservation);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void findViews() {

        autoComplete_num_room = findViewById(R.id.autoComplete_num_room);
        reserve_txt_input = findViewById(R.id.reserve_txt_input);
        reserve_confirm = findViewById(R.id.reserve_confirm);
        txt_user_review_input = findViewById(R.id.txt_user_review_input);
        original_price_reserve = findViewById(R.id.original_price_reserve);
        discount_price_reserve = findViewById(R.id.discount_price_reserve);
        tax_price_reserve = findViewById(R.id.tax_price_reserve);
        total_price_reserve = findViewById(R.id.total_price_reserve);
        txt_coupon_code_input = findViewById(R.id.txt_coupon_code_input);
        apply_coupon_button_reserve = findViewById(R.id.apply_coupon_button_reserve);
        reserve_hotel_city = findViewById(R.id.reserve_hotel_city);
        check_in_and_out_reserve = findViewById(R.id.check_in_and_out_reserve);
        num_guests_reserve = findViewById(R.id.num_guests_reserve);
        room_X_night_reserve = findViewById(R.id.room_X_night_reserve);
        check_in_date_reserve = findViewById(R.id.check_in_date_reserve);
        check_out_date_reserve = findViewById(R.id.check_out_date_reserve);
        back_button_reserve = findViewById(R.id.back_button_reserve);

    }


    //focus out of a text box when presses elsewhere
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}