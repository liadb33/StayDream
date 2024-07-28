package com.example.staydream.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.staydream.Fragments.HotelListFragment;
import com.example.staydream.R;

public class HotelViewActivity extends AppCompatActivity {


    public static final String KEY_SEARCH = "KEY_SEARCH";
    private FrameLayout hotel_FRAME_list;

    private HotelListFragment hotelListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_view);

        findViews();
        initViews();
    }

    private void initViews() {
        Intent intent = getIntent();
        String searchQuery = intent.getStringExtra(KEY_SEARCH);
        Log.d("SEARCH IS ->>", "initViews: " + searchQuery);
        hotelListFragment = new HotelListFragment(searchQuery);
        getSupportFragmentManager().beginTransaction().add(R.id.hotel_FRAME_list,hotelListFragment).commit();
    }

    private void findViews() {
        hotel_FRAME_list = findViewById(R.id.hotel_FRAME_list);
    }
}