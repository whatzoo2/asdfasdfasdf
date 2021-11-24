package com.gsh91.asdfasdfasdf.fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gsh91.asdfasdfasdf.G;
import com.gsh91.asdfasdfasdf.MainActivity;
import com.gsh91.asdfasdfasdf.Place;
import com.gsh91.asdfasdfasdf.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class Map1Fragment extends Fragment {

    GoogleMap gMap;
    SupportMapFragment supportMapFragment;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        supportMapFragment= (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {

                gMap=googleMap;

                MainActivity mainActivity = (MainActivity) getActivity();

//                LatLng ll = new LatLng(mainActivity.mylocation.getLatitude(), mainActivity.mylocation.getLongitude());
//                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 16));

                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                googleMap.setMyLocationEnabled(true);
//
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.getUiSettings().setZoomGesturesEnabled(true);
//
//                for (Place place : mainActivity.searchLocalApiResponse.documents){
//                    //double latitude=Double.parseDouble(place.)
//                }

                //입력된 주소



                //주소-->위도/경도 [Geocoding]
                Geocoder geocoder= new Geocoder(getActivity());
                try {

                    List<Address> addresses= geocoder.getFromLocationName(G.addr, 3);
                    Address address= addresses.get(0);

                    double latitude= address.getLatitude();
                    double longitude= address.getLongitude();

                    LatLng ll= new LatLng(latitude, longitude);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 16));

                    //마커추가
                    MarkerOptions options= new MarkerOptions().position(ll).title(G.addr);
                    googleMap.addMarker(options);


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "에러"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden){
            supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {

                    gMap=googleMap;

                    MainActivity mainActivity = (MainActivity) getActivity();

//                LatLng ll = new LatLng(mainActivity.mylocation.getLatitude(), mainActivity.mylocation.getLongitude());
//                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 16));

                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    googleMap.setMyLocationEnabled(true);
//
                    googleMap.getUiSettings().setZoomControlsEnabled(true);
                    googleMap.getUiSettings().setZoomGesturesEnabled(true);
//
//                for (Place place : mainActivity.searchLocalApiResponse.documents){
//                    //double latitude=Double.parseDouble(place.)
//                }

                    //입력된 주소

                    //주소-->위도/경도 [Geocoding]
                    Geocoder geocoder= new Geocoder(getActivity());
                    try {
                        List<Address> addresses= geocoder.getFromLocationName(G.addr, 3);
                        Address address= addresses.get(0);

                        double latitude= address.getLatitude();
                        double longitude= address.getLongitude();

                        LatLng ll= new LatLng(latitude, longitude);
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 16));

                        //마커추가
                        MarkerOptions options= new MarkerOptions().position(ll).title(G.addr);
                        googleMap.addMarker(options);



                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });

        }

    }
}
