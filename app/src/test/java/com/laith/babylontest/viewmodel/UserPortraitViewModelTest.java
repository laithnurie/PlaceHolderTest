package com.laith.babylontest.viewmodel;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;
import com.laith.babylontest.R;
import com.laith.babylontest.db.DBHelper;
import com.laith.babylontest.model.Address;
import com.laith.babylontest.model.Company;
import com.laith.babylontest.model.GeoCoords;
import com.laith.babylontest.model.User;
import com.laith.babylontest.network.ImageLoadUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ImageLoadUtil.class)
public class UserPortraitViewModelTest {

    @Mock
    View rootview;

    @Mock
    Context context;

    @Mock
    Bundle savedInstanceState;

    @Mock
    DBHelper dbHelper;

    @Mock
    private ImageView imgUser;

    @Mock
    private TextView txtName;

    @Mock
    private TextView txtUsername;

    @Mock
    private TextView txtEmail;

    @Mock
    private TextView txtPhone;

    @Mock
    private TextView txtWebsite;

    @Mock
    private TextView txtStreet;

    @Mock
    private TextView txtSuite;

    @Mock
    private TextView txtCity;

    @Mock
    private TextView txtZipcode;

    @Mock
    private TextView txtCompanyName;

    @Mock
    private TextView txtCompanyCatchPhrase;

    @Mock
    private TextView txtCompanyBusiness;

    @Mock
    private MapView mapView;

    private UserPortraitViewModel sut;

    @Before
    public void setUp() {
        initMocks(this);
        mockStatic(ImageLoadUtil.class);
        when(rootview.findViewById(R.id.img_user_avatar)).thenReturn(imgUser);
        when(rootview.findViewById(R.id.txt_user_name)).thenReturn(txtName);
        when(rootview.findViewById(R.id.txt_user_username)).thenReturn(txtUsername);
        when(rootview.findViewById(R.id.txt_user_email)).thenReturn(txtEmail);
        when(rootview.findViewById(R.id.txt_user_phone)).thenReturn(txtPhone);
        when(rootview.findViewById(R.id.txt_user_website)).thenReturn(txtWebsite);
        when(rootview.findViewById(R.id.txt_user_street)).thenReturn(txtStreet);
        when(rootview.findViewById(R.id.txt_user_suite)).thenReturn(txtSuite);
        when(rootview.findViewById(R.id.txt_user_city)).thenReturn(txtCity);
        when(rootview.findViewById(R.id.txt_user_zipcode)).thenReturn(txtZipcode);
        when(rootview.findViewById(R.id.txt_user_company_name)).thenReturn(txtCompanyName);
        when(rootview.findViewById(R.id.txt_user_company_catchphrase)).thenReturn(txtCompanyCatchPhrase);
        when(rootview.findViewById(R.id.txt_user_company_business)).thenReturn(txtCompanyBusiness);
        when(rootview.findViewById(R.id.map_user_location)).thenReturn(mapView);
    }

    @Test
    public void WhenShowingFullUserDetails() {
        User testUser = createUser(true, true, true);
        when(dbHelper.getFullUserInfo(0)).thenReturn(testUser);
        sut = new UserPortraitViewModel(rootview, dbHelper, context, null);
        sut.setUserID(testUser.getId());

        verify(txtName, times(1)).setText(testUser.getName());
        verify(txtUsername, times(1)).setText(testUser.getUsername());
        verify(txtEmail, times(1)).setText(testUser.getEmail());
        verify(txtPhone, times(1)).setText(testUser.getPhone());
        verify(txtWebsite, times(1)).setText(testUser.getWebsite());
        verify(txtStreet, times(1)).setText(testUser.getAddress().getStreet());
        verify(txtSuite, times(1)).setText(testUser.getAddress().getSuite());
        verify(txtCity, times(1)).setText(testUser.getAddress().getCity());
        verify(txtZipcode, times(1)).setText(testUser.getAddress().getZipcode());
        verify(txtCompanyName, times(1)).setText(testUser.getCompany().getName());
        verify(txtCompanyCatchPhrase, times(1)).setText(testUser.getCompany().getCatchPhrase());
        verify(txtCompanyBusiness, times(1)).setText(testUser.getCompany().getBusiness());

        verifyStatic(times(1));
        ImageLoadUtil.loadUserImage(testUser.getEmail(), context, imgUser);
    }

    @Test
    public void WhenShowingFullUserDetailsInSavedInstance() {
        User testUser = createUser(true, true, true);
        when(dbHelper.getFullUserInfo(0)).thenReturn(testUser);
        when(savedInstanceState.getParcelable("user")).thenReturn(testUser);
        sut = new UserPortraitViewModel(rootview, dbHelper, context, savedInstanceState);
        sut.setUserID(testUser.getId());
        verify(dbHelper, times(0)).getFullUserInfo(testUser.getId());


        verify(txtName, times(1)).setText(testUser.getName());
        verify(txtUsername, times(1)).setText(testUser.getUsername());
        verify(txtEmail, times(1)).setText(testUser.getEmail());
        verify(txtPhone, times(1)).setText(testUser.getPhone());
        verify(txtWebsite, times(1)).setText(testUser.getWebsite());
        verify(txtStreet, times(1)).setText(testUser.getAddress().getStreet());
        verify(txtSuite, times(1)).setText(testUser.getAddress().getSuite());
        verify(txtCity, times(1)).setText(testUser.getAddress().getCity());
        verify(txtZipcode, times(1)).setText(testUser.getAddress().getZipcode());
        verify(txtCompanyName, times(1)).setText(testUser.getCompany().getName());
        verify(txtCompanyCatchPhrase, times(1)).setText(testUser.getCompany().getCatchPhrase());
        verify(txtCompanyBusiness, times(1)).setText(testUser.getCompany().getBusiness());

        verifyStatic(times(1));
        ImageLoadUtil.loadUserImage(testUser.getEmail(), context, imgUser);
    }

    private User createUser(boolean hasAddress, boolean hasGeoCoords, boolean hasCompany) {

        User user = new User();
        user.setId(0);
        user.setName("test");
        user.setUsername("usernametest");
        user.setEmail("test@test.com");
        user.setPhone("07981491605");
        user.setWebsite("www.test.com");

        if (hasAddress) {
            Address address = new Address();
            address.setStreet("street");
            address.setSuite("suite");
            address.setCity("city");
            address.setZipcode("zipcode");

            if (hasGeoCoords) {
                GeoCoords geoCoords = new GeoCoords();
                geoCoords.setLat("-20");
                geoCoords.setLng("12");
                address.setGeo(geoCoords);
            }
            user.setAddress(address);
        }

        if (hasCompany) {
            Company company = new Company();
            company.setName("company test");
            company.setCatchPhrase("company catch phrase");
            company.setBusiness("company business");
            user.setCompany(company);
        }

        return user;
    }
}