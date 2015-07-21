package com.akurihine.exam.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.akurihine.exam.DealClickListener;
import com.akurihine.exam.R;
import com.akurihine.exam.model.Deal;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UnderTenView extends FrameLayout{

    private PeekingViewPager peekingViewPager;
    private DealClickListener dealClickListener;
    private List<Deal> deals;

    public UnderTenView(Context context) {
        super(context);
        init();
    }

    public UnderTenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public UnderTenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public UnderTenView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.under_ten, this);
        peekingViewPager = (PeekingViewPager) findViewById(R.id.pager);
        peekingViewPager.setPageTransformer(false, new UnderTenTransformer());
    }

    public void setData(List<Deal> deals) {
        this.deals = deals;
        peekingViewPager.setAdapter(new UnderTenPagerAdapter());
    }

    public void setDealClickListener(DealClickListener dealClickListener) {
        this.dealClickListener = dealClickListener;
    }

    private class UnderTenPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return deals.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final Deal deal = deals.get(position);

            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.under_ten_slide, container, false);
            ImageView image = (ImageView) view.findViewById(R.id.image);
            TextView price = (TextView) view.findViewById(R.id.price);
            TextView title = (TextView) view.findViewById(R.id.title);
            TextView merchant = (TextView) view.findViewById(R.id.merchant);

            Picasso.with(container.getContext()).load(deal.imageUrl).into(image);
            price.setText(deal.price);
            title.setText(deal.title);
            merchant.setText(deal.merchantName);

            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dealClickListener.onDealClicked(deal);
                }
            });

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private class UnderTenTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View page, float position) {
            int pageWidth = page.getWidth();
            if (position <= 1) { // [-1,1]
                View v = page.findViewById(R.id.image);
                if (v != null) v.setTranslationX(-position*pageWidth);
            }
        }
    }
}
