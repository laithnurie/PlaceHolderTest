package com.laith.babylontest.model;

public class Address {
    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private GeoCoords geo;

    public String getStreet() {
        return street;
    }

    public String getSuite() {
        return suite;
    }

    public String getCity() {
        return city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public GeoCoords getGeo() {
        return geo;
    }
}
