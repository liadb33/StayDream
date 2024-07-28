package com.example.staydream.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.staydream.Data.HotelDataManager;
import com.example.staydream.Models.HotelList;
import com.example.staydream.Interface.OnDataLoad;
import com.example.staydream.R;
import com.google.android.material.search.SearchBar;
import com.google.android.material.search.SearchView;

public class MainActivity extends AppCompatActivity {


    private SearchBar searchBar;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        initViews();
    }

    private void initViews() {
        searchBar.setOnClickListener(v -> searchView.show());
        searchView.getEditText().setOnEditorActionListener((v, actionId, event) -> {
            String query = searchView.getText().toString();
            performSearch(query);
            return true;
        });



    }

    private void performSearch(String query) {
        Intent intent = new Intent(this,HotelViewActivity.class);
        intent.putExtra(HotelViewActivity.KEY_SEARCH,query);
        startActivity(intent);
        finish();
    }

    private void findViews() {
        searchBar = findViewById(R.id.main_BAR_search);
        searchView = findViewById(R.id.main_searchView);
    }
}