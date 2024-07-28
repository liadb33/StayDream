package com.example.staydream.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.staydream.Adapters.ImageSliderAdapter;
import com.example.staydream.Fragments.MapFragment;
import com.example.staydream.Interface.OnMapAttachCallback;
import com.example.staydream.R;
import com.google.android.material.textview.MaterialTextView;

public class AboutHotelActivity extends AppCompatActivity {


    private ViewPager2 viewPager;

    public static final String KEY_NAME = "KEY_NAME";
    public static final String KEY_RATING = "KEY_RATING";
    public static final String KEY_LOCATION = "KEY_LOCATION";
    public static final String KEY_PHOTOS = "KEY_PHOTOS";
    public static final String KEY_LATITUDE = "KEY_LATITUDE";
    public static final String KEY_LONGITUDE = "KEY_LONGITUDE";

    private MaterialTextView about_TXT_name;
    private MaterialTextView about_TXT_location;
    private MaterialTextView about_STAR_txt;

    private FrameLayout about_FRAME_MAP;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_hotel);


        findViews();
        initViews();

    }

    private void initViews() {

        Intent previousIntent = getIntent();
        String[] hotelImages = previousIntent.getStringArrayExtra(KEY_PHOTOS);
        if(hotelImages != null){
            ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(hotelImages);
            viewPager.setAdapter(imageSliderAdapter);
        }

        about_TXT_name.setText(previousIntent.getStringExtra(KEY_NAME));
        about_STAR_txt.setText(String.valueOf(previousIntent.getDoubleExtra(KEY_RATING,0)));
        about_TXT_location.setText(previousIntent.getStringExtra(KEY_LOCATION));
        double latitude = previousIntent.getDoubleExtra(KEY_LATITUDE,0);
        double longitude = previousIntent.getDoubleExtra(KEY_LONGITUDE,0);

        mapFragment = new MapFragment(new OnMapAttachCallback() {
            @Override
            public void onMapAttached() {
                mapFragment.onMapZoom(latitude,longitude);
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.about_FRAME_MAP,mapFragment).commit();
    }

    private void findViews() {
        about_FRAME_MAP = findViewById(R.id.about_FRAME_MAP);
        about_TXT_name = findViewById(R.id.about_TXT_name);
        about_TXT_location = findViewById(R.id.about_TXT_location);
        about_STAR_txt = findViewById(R.id.about_STAR_txt);
        viewPager = findViewById(R.id.about_viewpager);
    }
}