package com.example.staydream.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.staydream.Fragments.HotelListFragment;
import com.example.staydream.R;

public class HotelViewActivity extends AppCompatActivity {

    public static final String KEY_FROM_DATE = "KEY_FROM_DATE";
    public static final String KEY_TO_DATE = "KEY_TO_DATE";
    public static final String KEY_SEARCH = "KEY_SEARCH";
    public static final String KEY_GUESTS = "KEY_GUESTS";
    private FrameLayout hotel_FRAME_list;
    private AppCompatImageButton back_button_hotel_view;
    private HotelListFragment hotelListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_view);

        findViews();
        initViews();
        setupOnBackPressed();
    }

    private void initViews() {
        Intent previousIntent = getIntent();
        String searchQuery = previousIntent.getStringExtra(KEY_SEARCH);
        String fromDate = previousIntent.getStringExtra(KEY_FROM_DATE);
        String toDate = previousIntent.getStringExtra(KEY_TO_DATE);
        int numGuests = previousIntent.getIntExtra(KEY_GUESTS,0);


        back_button_hotel_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        hotelListFragment = new HotelListFragment(searchQuery,fromDate,toDate,numGuests);
        getSupportFragmentManager().beginTransaction().add(R.id.hotel_FRAME_list,hotelListFragment).commit();
    }

    private void findViews() {
        hotel_FRAME_list = findViewById(R.id.hotel_FRAME_list);
        back_button_hotel_view = findViewById(R.id.back_button_hotel_view);
    }
    
    private void setupOnBackPressed() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Intent intent = new Intent(HotelViewActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}