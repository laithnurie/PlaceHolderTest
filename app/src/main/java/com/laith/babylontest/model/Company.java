package com.laith.babylontest.model;

import com.google.gson.annotations.SerializedName;

public class Company {
    private String name;
    private String catchPhrase;

    @SerializedName("bs")
    private String business;

    public String getName() {
        return name;
    }

    public String getCatchPhrase() {
        return catchPhrase;
    }

    public String getBusiness() {
        return business;
    }
}
