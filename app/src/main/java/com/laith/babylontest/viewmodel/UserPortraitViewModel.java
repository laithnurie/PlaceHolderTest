package com.laith.babylontest.viewmodel;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.laith.babylontest.R;
import com.laith.babylontest.db.DBHelper;
import com.laith.babylontest.model.Address;
import com.laith.babylontest.model.Company;
import com.laith.babylontest.model.GeoCoords;
import com.laith.babylontest.model.User;
import com.laith.babylontest.network.ImageLoadUtil;

public class UserPortraitViewModel implements UserViewModel, OnMapReadyCallback {

    private static String USER_KEY = "user";
    private DBHelper dbHelper;
    private Context context;
    private Bundle savedInstanceState;
    private User fullUser = null;

    private final ImageView imgUser;
    private final TextView txtName;
    private final TextView txtUsername;
    private final TextView txtEmail;
    private final TextView txtPhone;
    private final TextView txtWebsite;
    private final TextView txtStreet;
    private final TextView txtSuite;
    private final TextView txtCity;
    private final TextView txtZipcode;
    private final TextView txtCompanyName;
    private final TextView txtCompanyCatchPhrase;
    private final TextView txtCompanyBusiness;
    private final MapView mapView;


    public UserPortraitViewModel(View rootview, DBHelper dbHelper, Context context,
                                 Bundle savedInstanceState) {
        this.dbHelper = dbHelper;
        this.context = context;
        this.savedInstanceState = savedInstanceState;

        imgUser = (ImageView) rootview.findViewById(R.id.img_user_avatar);
        txtName = (TextView) rootview.findViewById(R.id.txt_user_name);
        txtUsername = (TextView) rootview.findViewById(R.id.txt_user_username);
        txtEmail = (TextView) rootview.findViewById(R.id.txt_user_email);
        txtPhone = (TextView) rootview.findViewById(R.id.txt_user_phone);
        txtWebsite = (TextView) rootview.findViewById(R.id.txt_user_website);
        txtStreet = (TextView) rootview.findViewById(R.id.txt_user_street);
        txtSuite = (TextView) rootview.findViewById(R.id.txt_user_suite);
        txtCity = (TextView) rootview.findViewById(R.id.txt_user_city);
        txtZipcode = (TextView) rootview.findViewById(R.id.txt_user_zipcode);
        txtCompanyName = (TextView) rootview.findViewById(R.id.txt_user_company_name);
        txtCompanyCatchPhrase = (TextView) rootview.findViewById(R.id.txt_user_company_catchphrase);
        txtCompanyBusiness = (TextView) rootview.findViewById(R.id.txt_user_company_business);
        mapView = (MapView) rootview.findViewById(R.id.map_user_location);
    }

    public void setUserID(int userID) {
        if (savedInstanceState != null && savedInstanceState.getParcelable(USER_KEY) != null) {
            fullUser = savedInstanceState.getParcelable(USER_KEY);
        } else {
            fullUser = dbHelper.getFullUserInfo(userID);
        }

        if (fullUser != null) {
            if (fullUser.getEmail() != null) {
                ImageLoadUtil.loadUserImage(fullUser.getEmail(), context, imgUser);
            }
            txtName.setText(fullUser.getName());
            txtUsername.setText(fullUser.getUsername());
            txtEmail.setText(fullUser.getEmail());
            txtPhone.setText(fullUser.getPhone());
            txtWebsite.setText(fullUser.getWebsite());
            if (fullUser.getAddress() != null) {
                Address address = fullUser.getAddress();
                txtStreet.setText(address.getStreet());
                txtSuite.setText(address.getSuite());
                txtCity.setText(address.getCity());
                txtZipcode.setText(address.getZipcode());
                mapView.getMapAsync(this);
            }

            if (fullUser.getCompany() != null) {
                Company company = fullUser.getCompany();
                txtCompanyName.setText(company.getName());
                txtCompanyCatchPhrase.setText(company.getCatchPhrase());
                txtCompanyBusiness.setText(company.getBusiness());
            }
        }
    }

    @Override
    public void onCreate() {
        Bundle mapViewSavedInstanceState =
                savedInstanceState != null ?
                        savedInstanceState.getBundle("mapViewSaveState") : null;
        mapView.onCreate(mapViewSavedInstanceState);
    }

    @Override
    public void onResume() {
        mapView.onResume();
    }

    @Override
    public void onPause() {
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Bundle mapViewSaveState = new Bundle(outState);
        mapView.onSaveInstanceState(mapViewSaveState);
        outState.putBundle("mapViewSaveState", mapViewSaveState);
        outState.putParcelable(USER_KEY, fullUser);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Address address = fullUser.getAddress();
        if (address != null && address.getGeo() != null) {
            final GeoCoords geoCoords = address.getGeo();

            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    LatLng latLng = new LatLng(Double.parseDouble(geoCoords.getLat()),
                            Double.parseDouble(geoCoords.getLng()));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                    googleMap.addMarker(new MarkerOptions().position(latLng));
                }
            });
        }
    }
}
