package com.example.liberty.contactlistview.adapter;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2016/9/12.
 */

public class BaseHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> viewArray;
//    private View itemView;
    public BaseHolder(View itemView) {
        super(itemView);
        viewArray=new SparseArray<>();
//        this.itemView=itemView;
    }

    public BaseHolder(ViewGroup parent,@LayoutRes int resId){
        super(LayoutInflater.from(parent.getContext()).inflate(resId,parent,false));
        viewArray=new SparseArray<>();
    }

    public  <T extends View>T getView(@IdRes int viewId){
        View view = viewArray.get(viewId);
        if (view == null) {
        view = itemView.findViewById(viewId);
        viewArray.put(viewId, view);
        }
        return (T) view;
    }

    public <M>void refreshData(List<M> dataList,int position){

    }

}
