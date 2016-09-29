package com.example.liberty.contactlistview.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liberty.contactlistview.R;
import com.example.liberty.contactlistview.bean.Friend;

import java.util.List;

/**
 * Created by LinJinFeng on 2016/9/29.
 */

public class ContactAdapter extends BaseRecyclerAdapter<Friend,BaseHolder> {
    public ContactAdapter(Context context) {
        super(context);
    }

    public ContactAdapter(Context context, List<Friend> dataList) {
        super(context, dataList);
    }

    @Override
    public BaseHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return new BaseHolder(parent, R.layout.contact_item);
    }

    @Override
    public void bindCustomViewHolder(BaseHolder holder, int position) {
        Friend friend=getItem(position);
        ((TextView)holder.getView(R.id.name)).setText(friend.getAccount());
        if (position==0){
            holder.getView(R.id.stick_container).setVisibility(View.VISIBLE);
            ((TextView)holder.getView(R.id.header)).setText(friend.getFirstPinyin());
        }else {
            if (!TextUtils.equals(friend.getFirstPinyin(),getItem(position-1).getFirstPinyin())){
                holder.getView(R.id.stick_container).setVisibility(View.VISIBLE);
                ((TextView)holder.getView(R.id.header)).setText(friend.getFirstPinyin());
            }else {
                holder.getView(R.id.stick_container).setVisibility(View.GONE);
            }
        }
        holder.itemView.setContentDescription(friend.getFirstPinyin());
    }

    @Override
    protected int getCustomViewType(int position) {
        return 0;
    }
}
