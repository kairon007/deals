package com.akurihine.exam.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.akurihine.exam.DealClickListener;
import com.akurihine.exam.R;
import com.akurihine.exam.model.Deal;
import com.squareup.picasso.Picasso;

public class SimpleCardView extends FrameLayout{

    public enum CardStyle {
        STRIPPED, DEFUALT
    }

    private Deal deal;
    private DealClickListener dealClickListener;
    private ImageView image;
    private TextView title;
    private TextView price;
    private TextView merchant;

    public SimpleCardView(Context context) {
        super(context);
        init();
    }

    public SimpleCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SimpleCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SimpleCardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.simple_deal_card, this);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dealClickListener != null) {dealClickListener.onDealClicked(deal);}
            }
        });
        image = (ImageView) findViewById(R.id.image);
        title = (TextView) findViewById(R.id.title);
        price = (TextView) findViewById(R.id.price);
        merchant = (TextView) findViewById(R.id.merchant);
    }

    public void setData(Deal deal) {
        this.deal = deal;
        Picasso.with(getContext()).load(deal.imageUrl).into(image);
        title.setText(deal.title);
        price.setText(deal.price);
        merchant.setText(deal.merchantName);
    }

    public void setStyle(CardStyle cardStyle) {
        switch (cardStyle) {
            case STRIPPED:
                image.setVisibility(GONE);
                break;
            case DEFUALT:
            default:
                image.setVisibility(VISIBLE);
                break;

        }
    }

    public void setDealClickListener(DealClickListener dealClickListener) {
        this.dealClickListener = dealClickListener;
    }
}
