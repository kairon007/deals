package com.akurihine.exam.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akurihine.exam.DealClickListener;
import com.akurihine.exam.R;
import com.akurihine.exam.model.Deal;
import com.squareup.picasso.Picasso;

public class GlossyCardView extends RelativeLayout {

    public enum CardStyle {
        SHORT, TALL
    }

    private Deal deal;
    private DealClickListener dealClickListener;
    private ImageView image;
    private TextView title;
    private TextView description;
    private TextView price;
    private TextView merchant;
    private int widthRatio = 3;
    private int heightRatio = 4;

    public GlossyCardView(Context context) {
        super(context);
        init();
    }

    public GlossyCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GlossyCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GlossyCardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.glossy_deal_card, this);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dealClickListener != null) {dealClickListener.onDealClicked(deal);}
            }
        });

        image = (ImageView) findViewById(R.id.image);
        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        price = (TextView) findViewById(R.id.price);
        merchant = (TextView) findViewById(R.id.merchant);
    }

    public void setData(Deal deal) {
        this.deal = deal;
        Picasso.with(getContext()).load(deal.imageUrl).into(image);
        title.setText(deal.title);
        description.setText(deal.description);
        price.setText(deal.price);
        merchant.setText(deal.merchantName);
    }

    public void setStyle(CardStyle cardStyle) {
        switch (cardStyle) {
            case TALL:
                widthRatio = 3;
                heightRatio = 4;
                description.setVisibility(VISIBLE);
                break;
            case SHORT:
            default:
                widthRatio = 4;
                heightRatio = 3;
                description.setVisibility(GONE);
                break;

        }
    }

    public void setDealClickListener(DealClickListener dealClickListener) {
        this.dealClickListener = dealClickListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width * heightRatio / widthRatio;
        final int heightSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightSpec);    }
}
