package com.gsh91.asdfasdfasdf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.util.Utility;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class LoginActivity extends AppCompatActivity {

    CircleImageView civ;
    TextView tvNickname;
    TextView tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        String keyHash= Utility.INSTANCE.getKeyHash(this);
        Log.i("KeyHash",keyHash);

        civ=findViewById(R.id.civ);
        tvNickname=findViewById(R.id.tv_nickname);
        tvEmail=findViewById(R.id.tv_email);

    }

    public void clickLogin(View view) {

        UserApiClient.getInstance().loginWithKakaoAccount(this, new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {

                if (oAuthToken != null){
                    Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();

                    UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
                        @Override
                        public Unit invoke(User user, Throwable throwable) {

                            if (user != null){
                                G.id=user.getId();
                                G.nickname=user.getKakaoAccount().getProfile().getNickname();
                                G.profileUrl=user.getKakaoAccount().getProfile().getProfileImageUrl();
                                G.email=user.getKakaoAccount().getEmail();

                                tvNickname.setText(G.nickname);
                                tvEmail.setText(G.email);
                                Glide.with(LoginActivity.this).load(G.profileUrl).into(civ);

                                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }

                            return null;
                        }
                    });

                }else{
                    Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                }

                return null;
            }
        });


    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("EXIT").setMessage("한번 더 누르면 종료됩니다.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNegativeButton("CANCEL",null).create().show();
    }

    //    public void clickBtn(View view) {
//        Intent intent=new Intent(this,MainActivity.class);
//        startActivity(intent);
//        finish();
//    }
}