package com.sure.pure;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.sure.pure.db.DatabaseHelper;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.model.ConfigSplash;

/**
 * Created by Creative IT Works on 20-Apr-17.
 */

public class Splash extends AwesomeSplash {
    public void initSplash(ConfigSplash configSplash) {
        configSplash.setBackgroundColor(R.color.colorPrimary);
        configSplash.setAnimCircularRevealDuration(2000);
        configSplash.setRevealFlagX(4);
        configSplash.setRevealFlagY(2);
        configSplash.setLogoSplash(R.mipmap.logo);
        configSplash.setAnimLogoSplashDuration(2000);
        configSplash.setOriginalHeight(1200);
        configSplash.setOriginalWidth(1200);
        configSplash.setAnimLogoSplashTechnique(Techniques.Landing);
        configSplash.setTitleSplash("Sure Pure");
        configSplash.setTitleTextColor(android.R.color.white);
        configSplash.setTitleTextSize(25.0f);
        configSplash.setAnimTitleDuration(1000);
        configSplash.setAnimTitleTechnique(Techniques.SlideInUp);
        configSplash.setTitleFont("fonts/Comfortaa_Bold.ttf");


    }

    public void animationsFinished() {
       /* startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
        finish();
*/
        Intent i=new Intent(Splash.this,MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
        finish();
    }
}