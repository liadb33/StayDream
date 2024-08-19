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

import com.example.staydream.Fragments.FavoriteHotelFragment;
import com.example.staydream.R;

public class FavoriteHotelActivity extends AppCompatActivity {

    public static final String KEY_FROM_DATE = "KEY_FROM_DATE";
    public static final String KEY_TO_DATE = "KEY_TO_DATE";
    public static final String KEY_GUESTS = "KEY_GUESTS";
    private FavoriteHotelFragment favoriteHotelFragment;
    private FrameLayout favorite_hotel_frame;
    private AppCompatImageButton back_button_favorite_hotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_hotel);

        findViews();
        initViews();
    }
    private void initViews() {

        Intent previousIntent = getIntent();
        String fromDate = previousIntent.getStringExtra(KEY_FROM_DATE);
        String toDate = previousIntent.getStringExtra(KEY_TO_DATE);
        int numGuests = previousIntent.getIntExtra(KEY_GUESTS,0);

        back_button_favorite_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FavoriteHotelActivity.this,MainActivity.class));
                finish();
            }
        });

        favoriteHotelFragment = new FavoriteHotelFragment(fromDate,toDate,numGuests);
        getSupportFragmentManager().beginTransaction().add(R.id.favorite_hotel_frame,favoriteHotelFragment).commit();
    }

    private void findViews() {
        favorite_hotel_frame = findViewById(R.id.favorite_hotel_frame);
        back_button_favorite_hotel = findViewById(R.id.back_button_favorite_hotel);
    }

    private void setupOnBackPressed() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Intent intent = new Intent(FavoriteHotelActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}