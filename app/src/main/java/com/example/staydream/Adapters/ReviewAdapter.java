package com.example.staydream.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.staydream.Models.UserReview;
import com.example.staydream.R;
import com.example.staydream.Utilities.ImageLoader;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private ArrayList<UserReview> userReviewsList;

    public ReviewAdapter(ArrayList<UserReview> userReviewsList) {
        this.userReviewsList = userReviewsList;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_review_item,parent,false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        UserReview userReview = getItem(position);

        ImageLoader.getInstance().load(userReview.getUserPhotoUrl(),holder.review_display_IMG);
        holder.review_display_name.setText(userReview.getUserNickname());
        holder.review_display_description.setText(userReview.getDescription());
    }

    private UserReview getItem(int position){
        return userReviewsList.get(position);
    }

    @Override
    public int getItemCount() {
        return userReviewsList == null ? 0 : userReviewsList.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        private final CircleImageView review_display_IMG;
        private final MaterialTextView review_display_name;
        private final MaterialTextView review_display_description;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);

            review_display_IMG = itemView.findViewById(R.id.review_display_IMG);
            review_display_name = itemView.findViewById(R.id.review_display_name);
            review_display_description = itemView.findViewById(R.id.review_display_description);

        }
    }
}
