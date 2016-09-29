package com.example.liberty.contactlistview;

import com.example.liberty.contactlistview.bean.Friend;
import com.example.liberty.contactlistview.common.PinYinComparator;
import com.example.liberty.contactlistview.common.Pinyin4jUtil;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void pinyinTest(){
        String pinyin=Pinyin4jUtil.convertToFirstSpell("乔布斯");
        System.out.println(pinyin);
    }

    @Test
    public void pinyinSortTest(){
//        String pinyin=Pinyin4jUtil.convertToFirstSpell("乔布斯");
//        String pinyin2=Pinyin4jUtil.convertToFirstSpell("Zhbit");
//        System.out.println(pinyin.compareTo(pinyin2)>0?pinyin:pinyin2);
        List<Friend> friends=new ArrayList<>();
        Friend friend=new Friend();
        friend.setAccount("韩永浩");
        friends.add(friend);
        Friend friend1=new Friend();
        friend1.setAccount("唐能发");
        friends.add(friend1);
        Friend friend2=new Friend();
        friend2.setAccount("林冠宏");
        friends.add(friend2);
        Friend friend4=new Friend();
        friend4.setAccount("&**");
        friends.add(friend4);
        Collections.sort(friends,new PinYinComparator());
        for (Friend friend3:friends){
            System.out.println(friend3.getAccount());
        }
    }
}