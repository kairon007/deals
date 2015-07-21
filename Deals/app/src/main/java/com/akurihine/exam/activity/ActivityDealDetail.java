package com.akurihine.exam.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.akurihine.exam.R;
import com.akurihine.exam.model.Deal;
import com.akurihine.exam.view.DealDetailsView;


public class ActivityDealDetail extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        DealDetailsView dealDetailsViewView = (DealDetailsView) findViewById(R.id.dealDetails);
        View clear = findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Deal deal = (Deal) getIntent().getSerializableExtra("DEAL");
        dealDetailsViewView.setData(deal);
    }
}
