package com.akurihine.exam.activity.Presenter;

import com.akurihine.exam.DealRetriever;
import com.akurihine.exam.activity.ActivityDealList;
import com.akurihine.exam.model.Deal;
import com.akurihine.exam.model.Deals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DealListPresenter {
    private DealRetriever dealRetriever;
    private ActivityDealList view;
    List<Deal> underTenDeals = new ArrayList<Deal>();
    List<Deal> deals = new ArrayList<Deal>();
    List<Integer> dealTypes = new ArrayList<Integer>();
    private String searchQuery;

    public DealListPresenter() {
        dealRetriever = new DealRetriever();
        getNewDeals();
    }

    public void setView(ActivityDealList view) {
        this.view = view;
        if (view != null) {
            view.onNewDeals(deals, underTenDeals, dealTypes);
        }
    }

    private void getNewDeals() {
        dealRetriever.getDeals(new DealRetriever.DataRetrieverListener() {
            @Override
            public void onResponse(Deals deals) {
                DealListPresenter.this.deals = deals;
                dealTypes = generateDealTypes(deals);
                underTenDeals = generateUnderTenDeals(deals);

                view.onNewDeals(deals, underTenDeals, dealTypes);
            }

            @Override
            public void onError() {
                view.showError();
            }
        });
    }

    public List<Integer> generateDealTypes(List<Deal> deals) {
        Random random = new Random();
        List<Integer> dealTypes = new ArrayList<Integer>();

        for(int i = 0; i < deals.size(); i++) {
            if(i % 3 == 0) {
                int max = 2;
                int min = 0;
                dealTypes.add(random.nextInt(max - min + 1) + min);
            } else {
                dealTypes.add(2);
            }
        }
        return dealTypes;
    }

    public List<Integer> generateDealTypesForSearch(List<Deal> deals) {
        List<Integer> dealTypes = new ArrayList<Integer>();

        for(int i = 0; i < deals.size(); i++) {
            if(dealTypes.size() == 0) {
                dealTypes.add(0);
            } else {
                dealTypes.add(2);
            }
        }
        return dealTypes;
    }

    public List<Deal> generateUnderTenDeals(List<Deal> deals) {
        List<Deal> underTenDeals = new ArrayList<Deal>();

        for(int i = 0; i < deals.size(); i++) {
            Deal deal = deals.get(i);

            if(Double.valueOf(deal.price) < 10.00d) {
                underTenDeals.add(deals.get(i));
                if (underTenDeals.size() >= 10) {
                    break;
                }
            }
        }
        return underTenDeals;
    }

    public List<Deal> getFilteredDeals(List<Deal> deals, String searchQuery) {
        List<Deal> newDeals = new ArrayList<Deal>();

        for (Deal deal : deals) {
            if (deal.title.toLowerCase().contains(searchQuery.toLowerCase())) {
                newDeals.add(deal);
            }
        }
        return newDeals;
    }

    public void retryClicked() {
        view.hideError();
        getNewDeals();
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
        if (searchQuery == null || searchQuery == "") {
            view.onNewDeals(deals, underTenDeals, dealTypes);
        } else {
            if(deals != null) {
                List<Deal> newDeals = getFilteredDeals(deals, searchQuery);
                List<Integer> newDealDisplayTypes = generateDealTypesForSearch(newDeals);

                view.onNewDeals(newDeals, null, newDealDisplayTypes);
            }
        }
    }
}
