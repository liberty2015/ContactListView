package com.example.liberty.contactlistview.common;

import com.example.liberty.contactlistview.bean.Friend;

import java.util.Comparator;

/**
 * Created by LinJinFeng on 2016/9/29.
 */

public class PinYinComparator implements Comparator<Friend> {
    @Override
    public int compare(Friend o1, Friend o2) {
        return o1.getPinyin().compareToIgnoreCase(o2.getPinyin());
    }
}
