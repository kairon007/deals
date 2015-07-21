package com.akurihine.exam.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import com.akurihine.exam.R;


public class PeekingViewPager extends ViewPager {

    private ViewConfiguration config;
    private int slop;
    private TypedArray attributes;
    private PagerAdapter internalPagerAdapter;
    private PagerAdapter pagerAdapter;
    private float peekingWidth;
    private float leftAlignment;
    private int ratioWidth = 1;
    private int ratioHeight = 1;
    private float startX;
    private float startY;
    private float currentX;
    private float currentY;
    private int spaceBetweenPages;
    private boolean isDragging;


    public PeekingViewPager(Context context) {
        super(context);
        this.config = ViewConfiguration.get(context);
        this.slop = ViewConfigurationCompat.getScaledPagingTouchSlop(config);
    }

    public PeekingViewPager(final Context context, AttributeSet attrs) {
        super(context, attrs);
        this.config = ViewConfiguration.get(context);
        this.slop = ViewConfigurationCompat.getScaledPagingTouchSlop(config);


        peekingWidth = getResources().getDimension(R.dimen.under_ten_peeking);
        spaceBetweenPages = (int) getResources().getDimension(R.dimen.standard_spacing);
        leftAlignment = (int) getResources().getDimension(R.dimen.standard_spacing);


        setOffscreenPageLimit(2);
        setPeekingWidth();

        internalPagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return pagerAdapter.getCount();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return pagerAdapter.isViewFromObject(view, object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                FrameLayout child = new FrameLayout(context);
                int adjustmentPadding = (int) (peekingWidth + spaceBetweenPages / 2);

                if (position == 0) {
                    child.setPadding((int) leftAlignment, 0, adjustmentPadding, 0);
                } else if (position == getCount()-1) {
                    child.setPadding(adjustmentPadding, 0, (int) leftAlignment, 0);
                }else {
                    child.setPadding(adjustmentPadding, 0, adjustmentPadding, 0);
                }
                container.addView(child);
                pagerAdapter.instantiateItem(child, position);
                return child;
            }

            @Override
            public void destroyItem(
                    ViewGroup container, int position, Object object) {pagerAdapter.destroyItem(container, position, object);
            }

            @Override
            public void finishUpdate(ViewGroup container) {
                pagerAdapter.finishUpdate(container);
            }

            @Override
            public void restoreState(Parcelable bundle, ClassLoader classLoader) {
                pagerAdapter.restoreState(bundle, classLoader);
            }

            @Override
            public Parcelable saveState() {
                return pagerAdapter.saveState();
            }

            @Override
            public void startUpdate(ViewGroup container) {
                pagerAdapter.startUpdate(container);
            }

            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
                pagerAdapter.setPrimaryItem(container, position, object);
            }

            @Override
            public void notifyDataSetChanged() {
                pagerAdapter.notifyDataSetChanged();
            }

        };

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = Math.round((width - (peekingWidth * 2) - spaceBetweenPages * 2) * ratioHeight / ratioWidth);
        final int heightSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handled = true;

        int action = event.getAction();
        switch(action) {
            case MotionEvent.ACTION_DOWN:
                isDragging = false;
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float xDiff, yDiff, yDiffScroll;

                currentX = event.getX();
                currentY = event.getY();

                xDiff = Math.abs(currentX - startX);
                yDiffScroll = startY - currentY;
                yDiff = Math.abs(yDiffScroll);

                // Let the pager's scroll take precedence if the user's horizontal swipe value
                // is greater than the user's vertical swipe value
                // Adjustments for slop, for less than perfect side scrolling.

                if((yDiff >= slop * 2) || xDiff <= yDiff) {
                    handled = false;
                } else {
                    isDragging = true;
                }

                startX = currentX;
                startY = currentY;
                break;
        }

        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(handled || isDragging);
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        pagerAdapter = adapter;
        if (pagerAdapter != null) {
            super.setAdapter(internalPagerAdapter);
        } else {
            super.setAdapter(null);
        }
    }

    @Override
    public PagerAdapter getAdapter() {
        return pagerAdapter;
    }

    private void setPeekingWidth() {
        super.setPageMargin((int) peekingWidth * -2);
    }

    @Override
    public void setPageMargin(int marginPixels) {
        spaceBetweenPages = marginPixels;
    }

    public float getPeekingWidth() {
        return peekingWidth;
    }

    public float getSpaceBetweenPages() {
        return spaceBetweenPages;
    }

    public void setDimensionRatio(int width, int height) {
        this.ratioWidth = width;
        this.ratioHeight = height;
    }
}