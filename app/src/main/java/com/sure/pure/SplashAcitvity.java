package com.sure.pure;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by Prakash on 7/7/2017.
 */

public class SplashAcitvity extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        imageView=(ImageView)findViewById(R.id.image);
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this,
                R.anim.zoomout);
        imageView.startAnimation(hyperspaceJumpAnimation);
    }
}
