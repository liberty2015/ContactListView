package com.example.liberty.contactlistview.bean;

import java.io.Serializable;
import java.io.StreamCorruptedException;

/**
 * Created by Administrator on 2016/9/28.
 */

public class Friend implements Serializable {
    private String remark;
    private String account;
    private String nickName;
    private String phoneNumber;
    private String area;
    private String headerUrl;
    private String pinyin;

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl;
    }

    public String getHeaderUrl() {
        return headerUrl;
    }

    public String getFirstPinyin(){
        return pinyin!=null?pinyin.substring(0,1):"";
    }
}
