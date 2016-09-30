package com.example.liberty.contactlistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.liberty.contactlistview.adapter.ContactAdapter;
import com.example.liberty.contactlistview.bean.Friend;
import com.example.liberty.contactlistview.common.PinYinComparator;
import com.example.liberty.contactlistview.common.Pinyin4jUtil;
import com.example.liberty.contactlistview.widget.CircleTextView;
import com.example.liberty.contactlistview.widget.DividerItemDecoration;
import com.example.liberty.contactlistview.widget.PinYinSlideView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    CircleTextView circleText;
    RecyclerView contactList;
    TextView header;
    ContactAdapter contactAdapter;
    List<Friend> friends;
    LinearLayoutManager manager;
    boolean move;
    int mIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circleText= (CircleTextView) findViewById(R.id.circleText);
        PinYinSlideView pinYinSlideView= (PinYinSlideView) findViewById(R.id.pinYinSlideView);
        friends=getFriendList();
        pinYinSlideView.setOnShowTextListener(new PinYinSlideView.OnShowTextListener() {
            @Override
            public void showText(String text) {
                circleText.setText(text);
                if (text!=null){
                    if (!text.equals("↑")){
                        int position=0;
                        boolean hasPinyin=false;
                        for (int i=0;i<friends.size();i++){
                            Friend friend=friends.get(i);
                            if (friend.getFirstPinyin().equals(text)){
                                position=i;
                                hasPinyin=true;
                                break;
                            }
                        }
                        if (hasPinyin){
                            MainActivity.this.scrollToPosition(position);
                        }
                    }else {
                        MainActivity.this.scrollToPosition(0);
                    }
                }

            }
        });
        contactList= (RecyclerView) findViewById(R.id.recycler);
        header= (TextView) findViewById(R.id.header);
        manager=new LinearLayoutManager(this);
        contactList.setLayoutManager(manager);
        contactAdapter=new ContactAdapter(this,friends);
        contactList.setAdapter(contactAdapter);
        contactList.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        contactList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager=recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager){
                    int firstItem=((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
//                    View itemView=recyclerView.getChildAt(firstItem);
//                    if (itemView!=null&&itemView.getContentDescription()!=null){
//                        header.setText(itemView.getContentDescription());
//                    }
                    Friend friend=contactAdapter.getItem(firstItem);
                    header.setText(friend.getFirstPinyin());
                    if (move){
                        move=false;
                        int n=mIndex-firstItem;
                        if (n>=0&&n<contactList.getChildCount()){
                            int top=contactList.getChildAt(n).getTop();
                            contactList.scrollBy(0,top);
                        }
                    }
                    //itemView.getId();
//                    header.setText(itemView.getContentDescription());
                }

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
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
                String pinyin=Pinyin4jUtil.convertToFirstSpell(input);
                if (Pinyin4jUtil.isPinYin(pinyin)){
                    friend.setPinyin(pinyin);
                }else {
                    friend.setPinyin("#");
                }
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

    private void scrollToPosition(int position){
        if (position>=0&&position<=friends.size()-1){
            int firstItem=manager.findFirstVisibleItemPosition();
            int lastItem=manager.findLastVisibleItemPosition();
            if (position<=firstItem){
                contactList.scrollToPosition(position);
            }else if (position<=lastItem){
                int top=contactList.getChildAt(position-firstItem).getTop();
                contactList.scrollBy(0,top);
            }else {
                contactList.scrollToPosition(position);
                mIndex=position;
                move=true;
            }
        }
    }
}
