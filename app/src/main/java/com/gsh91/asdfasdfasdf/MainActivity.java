package com.gsh91.asdfasdfasdf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.gsh91.asdfasdfasdf.fragments.AAAFragment;
import com.gsh91.asdfasdfasdf.fragments.GroupFragment;
import com.gsh91.asdfasdfasdf.fragments.HomeFragment;
import com.gsh91.asdfasdfasdf.fragments.Map1Fragment;

public class MainActivity extends AppCompatActivity {





    public BottomNavigationView bnv;

    public Fragment[] fragments=new Fragment[4];
    public FragmentManager fm;

    public AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this);

        adView=findViewById(R.id.adview);
        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        fragments[0]=new HomeFragment();

        fm=getSupportFragmentManager();
        FragmentTransaction tran =fm.beginTransaction();
        tran.add(R.id.fm1,fragments[0]);
        tran.commit();

        bnv=findViewById(R.id.bnv);
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentTransaction tran=fm.beginTransaction();

                tran.hide(fragments[0]);
                if (fragments[1]!=null)tran.hide(fragments[1]);
                if (fragments[2]!=null)tran.hide(fragments[2]);
                if (fragments[3]!=null)tran.hide(fragments[3]);



                switch (item.getItemId()){

                    case R.id.bnv_home:
                        tran.show(fragments[0]);
                        break;

                    case R.id.bnv_aaa:
                        if (fragments[1]==null){
                            fragments[1]=new AAAFragment();
                            tran.add(R.id.fm1,fragments[1]);
                        }
                        tran.show(fragments[1]);
                        break;

                    case R.id.bnv_group:
                        if (fragments[2]==null){
                            fragments[2]=new GroupFragment();
                            tran.add(R.id.fm1,fragments[2]);
                        }
                        tran.show(fragments[2]);
                        break;

                    case R.id.bnv_map1:
                        if (fragments[3]==null){
                            fragments[3]=new Map1Fragment();
                            tran.add(R.id.fm1,fragments[3]);
                        }
                        tran.show(fragments[3]);
                        break;




                }

                tran.commit();

                return true;
            }
        });

    }

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