package com.gsh91.asdfasdfasdf.fragments;

import android.app.AlertDialog;
import android.content.ClipData;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.gsh91.asdfasdfasdf.CCC;
import com.gsh91.asdfasdfasdf.Item;
import com.gsh91.asdfasdfasdf.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ArrayList<Item> items=new ArrayList<>();

    RecyclerView recyclerView;
    CCC cccAdapter;

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        //items.add(new Item(1,"aaa","bbb","ccc"));

        recyclerView = view.findViewById(R.id.recycler);
        cccAdapter = new CCC(getActivity(), items);
        recyclerView.setAdapter(cccAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        loadDB();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden){
            loadDB();
        }
    }

    void loadDB(){
        items.clear();
        cccAdapter.notifyDataSetChanged();

        new Thread(){
            @Override
            public void run() {
                String serverUrl="http://whatzoo.dothome.co.kr/aaa/loadDB.php";

                try {
                    URL url=new URL(serverUrl);

                    HttpURLConnection connection=(HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    connection.setUseCaches(false);

                    InputStream is=connection.getInputStream();
                    InputStreamReader isr= new InputStreamReader(is);
                    BufferedReader reader=new BufferedReader(isr);

                    final StringBuffer buffer=new StringBuffer();
                    String line=reader.readLine();
                    while (line!=null){
                        buffer.append(line+"\n");
                        line=reader.readLine();
                    }

//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            new AlertDialog.Builder(getActivity()).setMessage(buffer.toString()).create().show();
//                        }
//                    });

                    String data=buffer.toString();

                    String[] rows=data.split("@");
                    for (String row : rows){
                        String[] datas=row.split("&");
                        if (datas.length!=4) continue;

                        int no=Integer.parseInt(datas[0]);
                        String title=datas[1];
                        String msg=datas[2];
                        String date=datas[3];

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Item item=new Item(no,title,msg,date);
                                items.add(0,item);
                                cccAdapter.notifyItemInserted(0);
                            }
                        });

                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

}
