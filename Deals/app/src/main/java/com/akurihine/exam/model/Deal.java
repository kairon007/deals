package com.akurihine.exam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Deal implements Serializable{
    @JsonProperty("merchant_name")
    public String merchantName;

    @JsonProperty("title")
    public String title;

    @JsonProperty("description")
    public String description;

    @JsonProperty("image_url")
    public String imageUrl;

    @JsonIgnore
    public String price;

    @JsonProperty("price")
    public void setPrice(Double price) {
        this.price = formatPrice(price);
    }

    public String formatPrice(Double price) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(price);
    }
}