package com.gsh91.asdfasdfasdf.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gsh91.asdfasdfasdf.ChattingActivity;
import com.gsh91.asdfasdfasdf.ChattingAdapter;
import com.gsh91.asdfasdfasdf.GG;
import com.gsh91.asdfasdfasdf.MainActivity;
import com.gsh91.asdfasdfasdf.MessageItem;
import com.gsh91.asdfasdfasdf.R;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupFragment extends Fragment {

    private View view;
    private EditText et;
    private Button btn;
    private CircleImageView civ;

    private Uri imgUri;

    private boolean isFirst = true;
    private boolean isChanged = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_group, container, false);

        et = view.findViewById(R.id.et);
        civ = view.findViewById(R.id.iv);
        btn=view.findViewById(R.id.btn_send);

        loadData();

        if (GG.nickName != null) {
            et.setText(GG.nickName);
            Glide.with(this).load(GG.profileUrl).into(civ);
            isFirst=false;
        }

        civ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");

                resultLauncher.launch(intent);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFirst||isChanged){
                    saveData();
                }else{
                    Intent intent=new Intent(getActivity(),ChattingActivity.class);
                    startActivity(intent);
                }

                Toast.makeText(getActivity(), "반갑습니다. 매너채팅 해주세요.", Toast.LENGTH_LONG).show();
            }
        });






        return  view;
    }

    ActivityResultLauncher<Intent> resultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode()==Activity.RESULT_OK){
                Intent intent=result.getData();
                imgUri=intent.getData();

                Glide.with(GroupFragment.this).load(imgUri).into(civ);

                isChanged=true;
            }
        }
    });

    void saveData(){

        GG.nickName=et.getText().toString();

        if (imgUri==null) return;

        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmss");
        String fileName="IMG_"+sdf.format(new Date())+".png";

        FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
        StorageReference imgRef=firebaseStorage.getReference("profileImage/"+fileName);

        UploadTask uploadTask=imgRef.putFile(imgUri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        GG.profileUrl=uri.toString();
                        Toast.makeText(getActivity(), "프로필 이미지저장 완료", Toast.LENGTH_SHORT).show();

                        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                        DatabaseReference profilesRef=firebaseDatabase.getReference("profiles"  );

                        profilesRef.child(GG.nickName).setValue(GG.profileUrl);

                        SharedPreferences pref= getActivity().getSharedPreferences("account",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=pref.edit();

                        editor.putString("nickName",GG.nickName);
                        editor.putString("profileUrl",GG.profileUrl);

                        editor.commit();

                        Intent intent=new Intent(getActivity(),ChattingActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
            }
        });


    }







    void loadData(){
        SharedPreferences pref = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
        GG.nickName = pref.getString("nickName", null);
        GG.profileUrl = pref.getString("profileUrl", null);
    }
}
