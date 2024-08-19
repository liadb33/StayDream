package com.example.staydream.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.staydream.Fragments.ReservationListFragment;
import com.example.staydream.R;

public class UserReservationActivity extends AppCompatActivity {

    private FrameLayout user_reservations_FRAME;
    private ReservationListFragment reservationListFragment;

    private AppCompatImageButton back_button_user_reservation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reservation);

        findViews();
        initViews();
        setupOnBackPressed();
    }

    private void findViews(){
        user_reservations_FRAME = findViewById(R.id.user_reservations_FRAME);
        back_button_user_reservation = findViewById(R.id.back_button_user_reservation);
    }

    private void initViews(){

        back_button_user_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserReservationActivity.this,MainActivity.class));
                finish();
            }
        });

        reservationListFragment = new ReservationListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.user_reservations_FRAME,reservationListFragment).commit();
    }

    //when android's back button pressed, goes back to MainActivity.
    private void setupOnBackPressed() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Intent intent = new Intent(UserReservationActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // clearing any previous activities in the stack
                startActivity(intent);
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}