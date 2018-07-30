package com.sure.pure;

import android.content.Context;
import android.content.Intent;
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

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sure.pure.common.GlobalClass;
import com.sure.pure.model.Product;
import com.sure.pure.retrofit.APIInterface;
import com.sure.pure.utils.TouchImageView;

import java.util.ArrayList;

/**
 * Created by Bairavi on 7/22/2018.
 */

public class FullImage extends AppCompatActivity {
    private LinearLayout pager_indicator;
    ViewPager viewPager;
    private int dotsCount;
    private ImageView[] dots;
    Product p;
    ImageLoader loader;
    ImageView close;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullview);
        close=(ImageView)findViewById(R.id.close);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);

        loader=ImageLoader.getInstance();
        try {
            Intent i = getIntent();

            p = (Product) i.getSerializableExtra("product");
        }catch (Exception e)
        {

        }
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
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.full_item_view, collection, false);
            TouchImageView imageView=(TouchImageView)layout.findViewById(R.id.image);
            //imageView.setImageResource(drawables[position]);
            loader.displayImage(APIInterface.BASE_URL+drawables[position],imageView);
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
