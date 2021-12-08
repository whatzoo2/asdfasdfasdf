package com.gsh91.asdfasdfasdf.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.gsh91.asdfasdfasdf.MainActivity;
import com.gsh91.asdfasdfasdf.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AAAFragment extends Fragment {

    private View view;
    private EditText editTitle;
    private EditText editMsg;
    private EditText editPlace;
    private Button btn;
    Map1Fragment fmMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_aaa, container, false);

        editTitle=view.findViewById(R.id.et_title);
        editMsg=view.findViewById(R.id.et_msg);
        btn=view.findViewById(R.id.btn_save);
        editPlace=view.findViewById(R.id.et_place);
        fmMap=new Map1Fragment();



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle=new Bundle();
//                bundle.putString("homeFm","게시판");
                new Thread(){
                    @Override
                    public void run() {
                        String title=editTitle.getText().toString();
                        String msg=editMsg.getText().toString();
                        String place=editPlace.getText().toString();

                        String serverUrl="http://whatzoo.dothome.co.kr/asdfasdf/insertDB.php";

                        try {
                            URL url=new URL(serverUrl);

                            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                            connection.setRequestMethod("POST");
                            connection.setDoInput(true);
                            connection.setDoOutput(true);
                            connection.setUseCaches(false);

                            String data="title="+title+"&msg="+msg+"&place="+place;
                            OutputStream os=connection.getOutputStream();
                            OutputStreamWriter writer=new OutputStreamWriter(os);
                            writer.write(data,0,data.length());
                            writer.flush();
                            writer.close();

                            InputStream is=connection.getInputStream();
                            InputStreamReader isr=new InputStreamReader(is);

                            BufferedReader reader=new BufferedReader(isr);

                            final StringBuffer buffer=new StringBuffer();
                            String line=reader.readLine();
                            while (line!=null){
                                buffer.append(line+"\n");
                                line=reader.readLine();
                            }

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    MainActivity ac=(MainActivity) getActivity();
                                    ac.bnv.setSelectedItemId(R.id.bnv_home);

                                    editTitle.setText("");
                                    editMsg.setText("");
                                    editPlace.setText("");

                                }
                            });

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();



//                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
//                HomeFragment homeFragment=new HomeFragment();
//                homeFragment.setArguments(bundle);
//                transaction.replace(R.id.fm1,homeFragment);
//                transaction.commit();
            }
        });


        return view;
    }





}
