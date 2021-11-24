package com.gsh91.asdfasdfasdf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class ChattingActivity extends AppCompatActivity {

    ArrayList<MessageItem> messageItems= new ArrayList<>();
    ListView listView;
    ChattingAdapter chattingAdapter;

    EditText et;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference chatRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);


        getSupportActionBar().setTitle("오픈채팅방");
        getSupportActionBar().setSubtitle(GG.nickName);

        listView= findViewById(R.id.listview);
        chattingAdapter= new ChattingAdapter(this, messageItems);
        listView.setAdapter(chattingAdapter);

        et= findViewById(R.id.et);


        firebaseDatabase= FirebaseDatabase.getInstance();


        chatRef= firebaseDatabase.getReference("chat");


        chatRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                MessageItem item= snapshot.getValue(MessageItem.class);

                messageItems.add(item);

                chattingAdapter.notifyDataSetChanged();

                listView.setSelection(messageItems.size()-1);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    public void clickSend(View view) {

        String nickName= GG.nickName;
        String message= et.getText().toString();
        String profileUrl= GG.profileUrl;

        Calendar calendar= Calendar.getInstance();
        String time= calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);

        MessageItem item= new MessageItem(nickName, message, time, profileUrl);

        chatRef.push().setValue(item);

        et.setText("");

//        InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow( getCurrentFocus().getWindowToken() , 0 );

    }
}