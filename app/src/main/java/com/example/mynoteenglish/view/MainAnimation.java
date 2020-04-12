package com.example.mynoteenglish.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mynoteenglish.R;

public class MainAnimation extends AppCompatActivity {

    ImageView imageViewAnimation;
    TextView textViewAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_animation);
        Mapping();
        Run();

    }

    private void Run() {
        Context context;
        final Animation animation= AnimationUtils.loadAnimation(this, R.anim.anim_scale);
        final Animation animationtext= AnimationUtils.loadAnimation(MainAnimation.this, R.anim.anim_alpha);
        imageViewAnimation.startAnimation(animation);
        imageViewAnimation.performClick();
        Handler handler= new Handler();
        Handler handlerstart= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textViewAnimation.setVisibility(View.VISIBLE);
                textViewAnimation.startAnimation(animationtext);
            }
        }, animation.getDuration());
        handlerstart.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainAnimation.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, animation.getDuration()+animationtext.getDuration());
    }

    private void Mapping() {
     imageViewAnimation= findViewById(R.id.image_animation);
     textViewAnimation= findViewById(R.id.textview_animation);
    }

}
