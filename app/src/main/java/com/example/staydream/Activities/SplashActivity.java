package com.example.staydream.Activities;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.example.staydream.R;

public class SplashActivity extends AppCompatActivity {


    private LottieAnimationView splash_animation_lottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splash_animation_lottie = findViewById(R.id.splash_animation_lottie);

        splash_animation_lottie.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                finish();
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {

            }
        });

    }
}