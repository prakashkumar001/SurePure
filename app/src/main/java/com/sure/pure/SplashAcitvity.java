package com.sure.pure;

import android.content.Intent;
import android.content.SharedPreferences;
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
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        imageView=(ImageView)findViewById(R.id.image);
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this,
                R.anim.zoomout);

         imageView.startAnimation(hyperspaceJumpAnimation);

        hyperspaceJumpAnimation.setAnimationListener(new Animation.AnimationListener()
        {

            @Override
            public void onAnimationStart(Animation animation) {
                imageView.startAnimation(animation);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                String restoredText = prefs.getString("mobile", null);
                if (restoredText != null) {
                    String name = prefs.getString("email", "No name defined");//"No name defined" is the default value.

                    Intent i=new Intent(SplashAcitvity.this,MainActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                    finish();
                }else
                {
                    Intent i=new Intent(SplashAcitvity.this,Register.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                    finish();
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
