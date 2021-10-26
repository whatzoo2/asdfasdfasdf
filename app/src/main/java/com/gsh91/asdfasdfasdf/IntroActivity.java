package com.gsh91.asdfasdfasdf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class IntroActivity extends AppCompatActivity {

    ImageView iv;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        iv=findViewById(R.id.iv);
        tv=findViewById(R.id.tv);

        Animation ani= AnimationUtils.loadAnimation(this,R.anim.ccc_logo);
        iv.startAnimation(ani);

        ani.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent=new Intent(IntroActivity.this,LoginActivity.class);
                startActivity(intent);

                finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }
}