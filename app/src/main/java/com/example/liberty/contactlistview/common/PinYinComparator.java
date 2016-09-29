package com.example.liberty.contactlistview.common;

import com.example.liberty.contactlistview.bean.Friend;

import java.util.Comparator;

/**
 * Created by LinJinFeng on 2016/9/29.
 */

public class PinYinComparator implements Comparator<Friend> {
    @Override
    public int compare(Friend o1, Friend o2) {
        String pinyin1=Pinyin4jUtil.convertToFirstSpell(o1.getAccount());
        String pinyin2=Pinyin4jUtil.convertToFirstSpell(o2.getAccount());
        return pinyin1.compareToIgnoreCase(pinyin2);
    }
}
