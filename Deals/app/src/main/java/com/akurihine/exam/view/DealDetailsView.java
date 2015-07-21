package com.akurihine.exam.view;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akurihine.exam.R;
import com.akurihine.exam.model.Deal;
import com.squareup.picasso.Picasso;

public class DealDetailsView extends RelativeLayout{

    private ImageView image;
    private TextView title;
    private TextView description;
    private TextView price;
    private TextView merchant;

    public DealDetailsView(Context context) {
        super(context);
        init();
    }

    public DealDetailsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DealDetailsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DealDetailsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.deal_details, this);

        image = (ImageView) findViewById(R.id.image);
        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        price = (TextView) findViewById(R.id.price);
        merchant = (TextView) findViewById(R.id.merchant);
    }

    public void setData(Deal deal) {
        Picasso.with(getContext()).load(deal.imageUrl).into(image);
        title.setText(deal.title);
        description.setText(deal.description);
        price.setText(deal.price);
        merchant.setText(deal.merchantName);
    }
}
