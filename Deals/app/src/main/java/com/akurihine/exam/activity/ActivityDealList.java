package com.akurihine.exam.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.SearchView;


import com.akurihine.exam.DealClickListener;
import com.akurihine.exam.R;
import com.akurihine.exam.SpaceItemDecoration;
import com.akurihine.exam.activity.Presenter.DealListPresenter;
import com.akurihine.exam.adapter.DealListAdapter;
import com.akurihine.exam.model.Deal;

import java.util.List;

//Presenter is used to make business logic more unit testable
// and detach retrieval process from activity lifecycle

public class ActivityDealList extends AppCompatActivity implements DealClickListener {

    private static final String tag = ActivityDealList.class.getName();
    List<Deal> deals;
    List<Deal> underTenDeals;
    List<Integer> dealTypes;
    private RecyclerView list;
    private DealListAdapter listAdapter;
    private static DealListPresenter dealListPresenter;
    private View fullScreenMessage;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (RecyclerView) findViewById(R.id.list);
        fullScreenMessage = findViewById(R.id.fullScreenMessage);

        list.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelOffset(R.dimen.standard_spacing)));
        list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        if (dealListPresenter == null) {
            dealListPresenter = new DealListPresenter();
        }

        dealListPresenter.setView(this);
    }

    @Override
    protected void onDestroy() {
        dealListPresenter.setView(null);
        super.onDestroy();
    }

    public void onNewDeals(List<Deal> deals, List<Deal> underTenDeals, List<Integer> dealTypes) {
        this.deals = deals;
        this.underTenDeals = underTenDeals;
        this.dealTypes = dealTypes;

        AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(500);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override  public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
                fadeIn.setDuration(500);

                listAdapter = new DealListAdapter(ActivityDealList.this.deals,
                        ActivityDealList.this.dealTypes,
                        ActivityDealList.this.underTenDeals,
                        ActivityDealList.this);
                list.setAdapter(listAdapter);
                list.startAnimation(fadeIn);
            }
        });

        list.startAnimation(fadeOut);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem search = menu.findItem(R.id.search);

        MenuItemCompat.setOnActionExpandListener(search, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                dealListPresenter.setSearchQuery(null);
                return true;
            }
        });
        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dealListPresenter.setSearchQuery(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setIconifiedByDefault(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDealClicked(Deal deal) {
        Intent intent = new Intent(ActivityDealList.this, ActivityDealDetail.class);
        intent.putExtra("DEAL", deal);
        startActivity(intent);
    }

    public void hideError() {
        AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(250);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override  public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                fullScreenMessage.setVisibility(View.GONE);
            }
        });

        fullScreenMessage.startAnimation(fadeOut);
    }

    public void showError() {
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(250);

        fullScreenMessage.setVisibility(View.VISIBLE);
        fullScreenMessage.startAnimation(fadeIn);

        findViewById(R.id.callToAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealListPresenter.retryClicked();
            }
        });
    }
}
