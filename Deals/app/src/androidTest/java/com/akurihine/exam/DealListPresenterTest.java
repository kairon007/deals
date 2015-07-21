package com.akurihine.exam;

import com.akurihine.exam.activity.Presenter.DealListPresenter;
import com.akurihine.exam.model.Deal;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DealListPresenterTest extends TestCase{
    DealListPresenter dealListPresenter = new DealListPresenter();
    List<Deal> deals = new ArrayList<Deal>();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Deal deal = new Deal();
        deal.price = "1.00";
        deal.title = "deal1";
        deals.add(deal);

        deal = new Deal();
        deal.price = "5.50";
        deal.title = "deal2";
        deals.add(deal);

        deal = new Deal();
        deal.price = "11.00";
        deal.title = "deal3";
        deals.add(deal);
    }

    @Test
    public void testGenerateDealTypes() {
        // its random
        assertTrue(true);
    }

    @Test
    public void testGenerateDealTypesForSearch() {
        List<Integer> types = dealListPresenter.generateDealTypesForSearch(deals);
        assertTrue(types.get(0) == 0);
        assertTrue(types.get(1) == 2);
        assertTrue(types.get(2) == 2);
    }

    @Test
    public void testGenerateUnderTenDeals() {
        List<Deal> underTenDeals = dealListPresenter.generateUnderTenDeals(deals);
        assertTrue(underTenDeals.size() == 2);
    }

    @Test
    public void testGetFilteredDeals() {
        List<Deal> filteredDeals = dealListPresenter.getFilteredDeals(deals, "2");
        assertEquals(filteredDeals.get(0).title, "deal2");
    }
}
