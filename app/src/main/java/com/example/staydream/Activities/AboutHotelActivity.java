package com.example.staydream.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.viewpager2.widget.ViewPager2;

import com.example.staydream.Adapters.ImageSliderAdapter;
import com.example.staydream.Adapters.ViewNavAdapter;
import com.example.staydream.Fragments.DetailsFragment;
import com.example.staydream.Fragments.MapFragment;
import com.example.staydream.Fragments.OverviewFragment;
import com.example.staydream.Fragments.ReviewFragment;
import com.example.staydream.Interface.OnMapAttachCallback;
import com.example.staydream.Models.Hotel;
import com.example.staydream.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textview.MaterialTextView;

import me.relex.circleindicator.CircleIndicator3;

public class AboutHotelActivity extends AppCompatActivity {

    public static final String KEY_HOTEL = "KEY_HOTEL";
    public static final String KEY_FROM_DATE = "KEY_FROM_DATE";
    public static final String KEY_TO_DATE = "KEY_TO_DATE";
    public static final String KEY_GUESTS = "KEY_GUESTS";
    private ViewPager2 about_viewpager;
    private ViewPager2 about_NAV_viewpager;
    private TabLayout about_NAV_tabLayout;
    private CircleIndicator3 about_circle_indicator;
    private Hotel aboutHotel;
    private String fromDate;
    private String toDate;
    private int numGuests;
    private MaterialTextView about_price;
    private ExtendedFloatingActionButton about_BOOK;
    private MaterialTextView about_TXT_name;
    private MaterialTextView about_TXT_location;
    private MaterialTextView about_STAR_txt;

    private AppCompatImageButton back_button_about_hotel;
    private FrameLayout about_FRAME_MAP;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_hotel);

        findViews();
        initViews();
    }

    @SuppressLint("SetTextI18n")
    private void initViews() {


        Intent previousIntent = getIntent();
        aboutHotel = previousIntent.getParcelableExtra(KEY_HOTEL);
        fromDate = previousIntent.getStringExtra(KEY_FROM_DATE);
        toDate = previousIntent.getStringExtra(KEY_TO_DATE);
        numGuests = previousIntent.getIntExtra(KEY_GUESTS,0);

        //sets adapter for image slider
        String[] hotelImages = aboutHotel.getImageList().toArray(new String[0]);
        if(hotelImages.length != 0){
            ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(hotelImages);
            about_viewpager.setAdapter(imageSliderAdapter);
            about_circle_indicator.setViewPager(about_viewpager);
        }


        back_button_about_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //name and location
        about_TXT_name.setText(aboutHotel.getHotel_name());
        about_STAR_txt.setText(String.valueOf(aboutHotel.getRating_average()));
        about_TXT_location.setText(aboutHotel.getCountry() +", " + aboutHotel.getCity());
        about_BOOK.setOnClickListener(v -> transactToReserve());
        about_price.setText(aboutHotel.getPrice() + "$/Night");
        double latitude = aboutHotel.getLatitude();
        double longitude = aboutHotel.getLongitude();


        //map fragment
        mapFragment = new MapFragment(new OnMapAttachCallback() {
            @Override
            public void onMapAttached() {
                mapFragment.onMapZoom(latitude,longitude);
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.about_FRAME_MAP,mapFragment).commit();

        setTabNavigator();
    }


    //transaction to confirm reservation
    private void transactToReserve(){
        Intent intent = new Intent(this, ReserveHotelActivity.class);
        intent.putExtra(ReserveHotelActivity.KEY_HOTEL,aboutHotel);
        intent.putExtra(ReserveHotelActivity.KEY_FROM_DATE,fromDate);
        intent.putExtra(ReserveHotelActivity.KEY_TO_DATE,toDate);
        intent.putExtra(ReserveHotelActivity.KEY_GUESTS,numGuests);
        startActivity(intent);
        finish();
    }


    //sets tabslayout and viewpager
    private void setTabNavigator() {

        ViewNavAdapter viewNavAdapter = new ViewNavAdapter(this);

        viewNavAdapter.addFragment(new OverviewFragment(aboutHotel),"Overview");
        viewNavAdapter.addFragment(new DetailsFragment(aboutHotel),"Details");
        viewNavAdapter.addFragment(new ReviewFragment(aboutHotel),"Reviews");

        about_NAV_viewpager.setAdapter(viewNavAdapter);
        new TabLayoutMediator(about_NAV_tabLayout,about_NAV_viewpager,
                (tab, position) -> tab.setText(viewNavAdapter.getPageTitle(position))).attach();
    }

    private void findViews() {
        about_FRAME_MAP = findViewById(R.id.about_FRAME_MAP);
        about_TXT_name = findViewById(R.id.about_TXT_name);
        about_TXT_location = findViewById(R.id.about_TXT_location);
        about_STAR_txt = findViewById(R.id.about_STAR_txt);
        about_viewpager = findViewById(R.id.about_viewpager);
        about_NAV_viewpager = findViewById(R.id.about_NAV_viewpager);
        about_NAV_tabLayout = findViewById(R.id.about_NAV_tabLayout);
        about_BOOK = findViewById(R.id.about_BOOK);
        about_price = findViewById(R.id.about_price);
        about_circle_indicator = findViewById(R.id.about_circle_indicator);
        back_button_about_hotel = findViewById(R.id.back_button_about_hotel);
    }


}