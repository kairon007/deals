package com.akurihine.exam;

import com.akurihine.exam.model.Deal;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;

public class DealTest extends TestCase{

    @Test
    public void testFormatPrice() {
        Deal deal =  new Deal();

        Double price = 1.0;
        deal.setPrice(price);
        Assert.assertEquals(deal.price, "1.00");

        price = .071;
        deal.setPrice(price);
        Assert.assertEquals(deal.price, ".07");

        price = 1299.;
        deal.setPrice(price);
        Assert.assertEquals(deal.price, "1299.00");
    }
}