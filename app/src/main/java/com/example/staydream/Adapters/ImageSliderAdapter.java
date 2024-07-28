package com.example.staydream.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.staydream.R;
import com.example.staydream.Utilities.ImageLoader;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder> {

    private final static int NUM_PHOTOS = 5;

    private String[] imageUrls;

    public ImageSliderAdapter(String[] imageUrls) {
        this.imageUrls = imageUrls;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_slide_img_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

//        Log.d("PHOTOS URL", imageUrls[position] + "  ");
        ImageLoader.getInstance().load(imageUrls[position],holder.image_SLD_item);
    }
    
    @Override
    public int getItemCount() {
        return imageUrls == null ? 0 : imageUrls.length;
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        private final ShapeableImageView image_SLD_item;

        ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            image_SLD_item = itemView.findViewById(R.id.image_SLD_item);
        }
    }
}
