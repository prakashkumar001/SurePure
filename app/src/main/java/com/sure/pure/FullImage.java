package com.sure.pure;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.sure.pure.common.GlobalClass;
import com.sure.pure.utils.TouchImageView;

import java.util.ArrayList;

/**
 * Created by Bairavi on 7/22/2018.
 */

public class FullImage extends AppCompatActivity {

    ViewPager viewPager; private LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullview);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewPager.setAdapter(new CustomPagerAdapter(this));
        setUiPageViewController();
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(getResources().getDrawable(R.drawable.unselected_dot));
                }

                dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selected_dot));


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public class CustomPagerAdapter extends PagerAdapter {

        private Context mContext;
        String[]   drawables=new String[]{p.getProductimage(),p.getProductimage(),p.getProductimage()};
        public CustomPagerAdapter(Context context) {
            mContext = context;
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {

            LayoutInflater inflater = LayoutInflater.from(mContext);
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.view_item, collection, false);
            TouchImageView imageView=(TouchImageView)layout.findViewById(R.id.image);
            //imageView.setImageResource(drawables[position]);
            loader.displayImage("http://www.boolfox.com/rest"+drawables[position],imageView);
            collection.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return drawables.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


    }

    private void setUiPageViewController() {

        dotsCount = 3;
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.unselected_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selected_dot));
    }

}
