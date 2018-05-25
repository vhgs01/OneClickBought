package br.com.hugo.victor.oneclickbought.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Product {

    private String productName;
    private String productPhoto;
    private String latitude;
    private String longitude;

    public Product() {}

    public Product(String productName, String productPhoto, String latitude, String longitude) {
        this.productName = productName;
        this.productPhoto = productPhoto;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
