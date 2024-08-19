package com.example.staydream.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.staydream.Adapters.ReviewAdapter;
import com.example.staydream.Data.DataManager;
import com.example.staydream.Interface.OnDataLoadReview;
import com.example.staydream.Models.Hotel;
import com.example.staydream.Models.UserReview;
import com.example.staydream.R;

import java.util.ArrayList;

public class ReviewFragment extends Fragment {

    private ArrayList<UserReview> userReviewsList;
    private RecyclerView fragment_LST_reviews;
    private Hotel hotel;
    public ReviewFragment() {
        // Required empty public constructor
    }

    public ReviewFragment(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_review, container, false);

        findViews(v);
        initViews();
        return v;
    }

    private void initViews() {
        loadHotelReviews();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        fragment_LST_reviews.setLayoutManager(linearLayoutManager);
    }


    //loads the reviews
    private void loadHotelReviews() {
        DataManager.getInstance().setOnReviewsLoaded(new OnDataLoadReview() {
            @Override
            public void onDataLoadReview(ArrayList<UserReview> userReviewsList) {
                ReviewAdapter reviewAdapter = new ReviewAdapter(userReviewsList);
                fragment_LST_reviews.setAdapter(reviewAdapter);
            }
        });

        DataManager.getInstance().loadReviewsFromDB(hotel.getHotel_id());
    }

    private void findViews(View view) {
        fragment_LST_reviews = view.findViewById(R.id.fragment_LST_reviews);
    }
}