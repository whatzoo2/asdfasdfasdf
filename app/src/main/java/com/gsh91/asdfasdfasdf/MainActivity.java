package com.gsh91.asdfasdfasdf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.gsh91.asdfasdfasdf.fragments.AAAFragment;
import com.gsh91.asdfasdfasdf.fragments.GroupFragment;
import com.gsh91.asdfasdfasdf.fragments.HomeFragment;
import com.gsh91.asdfasdfasdf.fragments.Map1Fragment;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {


    public Location mylocation;

    public String searchQuery;

    public SearchLocalApiResponse searchLocalApiResponse;


    public BottomNavigationView bnv;

    public Fragment[] fragments = new Fragment[4];
    public FragmentManager fm;

    public AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MobileAds.initialize(this);

        adView = findViewById(R.id.adview);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        fragments[0] = new HomeFragment();

        fm = getSupportFragmentManager();
        FragmentTransaction tran = fm.beginTransaction();
        tran.add(R.id.fm1, fragments[0]);
        tran.commit();

        bnv = findViewById(R.id.bnv);
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentTransaction tran = fm.beginTransaction();

                tran.hide(fragments[0]);
                if (fragments[1] != null) tran.hide(fragments[1]);
                if (fragments[2] != null) tran.hide(fragments[2]);
                if (fragments[3] != null) tran.hide(fragments[3]);


                switch (item.getItemId()) {

                    case R.id.bnv_home:
                        tran.show(fragments[0]);
                        break;

                    case R.id.bnv_aaa:
                        if (fragments[1] == null) {
                            fragments[1] = new AAAFragment();
                            tran.add(R.id.fm1, fragments[1]);
                        }
                        tran.show(fragments[1]);
                        break;

                    case R.id.bnv_group:
                        if (fragments[2] == null) {
                            fragments[2] = new GroupFragment();
                            tran.add(R.id.fm1, fragments[2]);
                        }
                        tran.show(fragments[2]);
                        break;

                    case R.id.bnv_map1:
                        if (fragments[3] == null) {
                            fragments[3] = new Map1Fragment();
                            tran.add(R.id.fm1, fragments[3]);
                        }
                        tran.show(fragments[3]);
                        break;


                }

                tran.commit();

                return true;
            }
        });

//        위치정보 제공 동적퍼미션
        if (Build.VERSION.SDK_INT >= 23) {
            String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
            int checkResult = checkSelfPermission(permissions[0]);
            if (checkResult == PackageManager.PERMISSION_DENIED)
                requestPermissions(permissions, 41);
            else requestMyLocation();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 41) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestMyLocation();
            } else {
                Toast.makeText(this, "내위치 검색 안돼", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    FusedLocationProviderClient fusedLocationProviderClient;

    void requestMyLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

    }

    LocationCallback locationCallback=new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull @NotNull LocationResult locationResult) {
            super.onLocationResult(locationResult);

            mylocation=locationResult.getLastLocation();
//            Toast.makeText(MainActivity.this, ""+mylocation.getLatitude(), Toast.LENGTH_SHORT).show();

            fusedLocationProviderClient.removeLocationUpdates(locationCallback);



        }
    };

//    void searchPlace(){
//
//        Retrofit.Builder builder=new Retrofit.Builder();
//        builder.baseUrl("http://dapi.kakao.com");
//        builder.addConverterFactory(ScalarsConverterFactory.create());
//        builder.addConverterFactory(GsonConverterFactory.create());
//        Retrofit retrofit=builder.build();
//
//        RetrofitService retrofitService=retrofit.create(RetrofitService.class);
//        Call<SearchLocalApiResponse> call =retrofitService.searchPlace(searchQuery,mylocation.getLongitude()+"",mylocation.getLatitude()+"");
//        call.enqueue(new Callback<SearchLocalApiResponse>() {
//            @Override
//            public void onResponse(Call<SearchLocalApiResponse> call, Response<SearchLocalApiResponse> response) {
//                searchLocalApiResponse=response.body();
//            }
//
//            @Override
//            public void onFailure(Call<SearchLocalApiResponse> call, Throwable t) {
//
//                Toast.makeText(MainActivity.this, "서버에 오류가 있습니다.\n 잠시 뒤에 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//





    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        new AlertDialog.Builder(this).setTitle("EXIT").setMessage("한번 더 누르면 종료됩니다.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNegativeButton("CANCEL",null).create().show();

    }
}