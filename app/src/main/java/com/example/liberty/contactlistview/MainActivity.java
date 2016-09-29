package com.example.liberty.contactlistview;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.liberty.contactlistview.adapter.ContactAdapter;
import com.example.liberty.contactlistview.bean.Friend;
import com.example.liberty.contactlistview.common.PinYinComparator;
import com.example.liberty.contactlistview.widget.CircleTextView;
import com.example.liberty.contactlistview.widget.DividerItemDecoration;
import com.example.liberty.contactlistview.widget.PinYinSlideView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    CircleTextView circleText;
    RecyclerView contactList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circleText= (CircleTextView) findViewById(R.id.circleText);
        PinYinSlideView pinYinSlideView= (PinYinSlideView) findViewById(R.id.pinYinSlideView);
        pinYinSlideView.setOnShowTextListener(new PinYinSlideView.OnShowTextListener() {
            @Override
            public void showText(String text) {
                circleText.setText(text);
            }
        });
        contactList= (RecyclerView) findViewById(R.id.recycler);
        List<Friend> friends=getFriendList();
        contactList.setLayoutManager(new LinearLayoutManager(this));
        ContactAdapter contactAdapter=new ContactAdapter(this,friends);
        contactList.setAdapter(contactAdapter);
        contactList.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
    }

    private List<Friend> getFriendList(){
        List<Friend> friends=new ArrayList<>();
        InputStream inputStream=getResources().openRawResource(R.raw.names);
        BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
        String input=null;
        try {
            while ((input=reader.readLine())!=null){
                Friend friend=new Friend();
                friend.setAccount(input);
                friends.add(friend);
            }
            if (friends.size()>1){
                Collections.sort(friends,new PinYinComparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return friends;
    }
}
