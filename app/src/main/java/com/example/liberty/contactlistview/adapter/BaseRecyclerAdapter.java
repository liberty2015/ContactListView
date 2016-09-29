package com.example.liberty.contactlistview.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/12.
 */

public abstract class BaseRecyclerAdapter<M,VH extends BaseHolder> extends RecyclerView.Adapter<BaseHolder> {

    protected View headerView;
    protected View footerView;

    private List<M> dataList;

    private Context mContext;
    protected OnItemClickListener onItemClickListener;

    public static final int VIEW_HEADER=1023;
    public static final int VIEW_FOOTER=1024;


    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onHeaderClick();
        void onFooterClick();
    }

    public BaseRecyclerAdapter(Context context){
        mContext=context;
        this.dataList=new ArrayList<>();
    }

    public BaseRecyclerAdapter(Context context,List<M> dataList){
        mContext=context;
        this.dataList=new ArrayList<>();
        this.dataList.addAll(dataList);
    }

    public void fillDataList(List<M> datas){
        dataList.clear();
        if (dataList.addAll(datas)){
            notifyDataSetChanged();
//            notifyItemInserted(getExtraHeaderFooterItemCount());
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public Context getmContext() {
        return mContext;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==VIEW_HEADER){
            return new BaseHolder(headerView);
        }else if (viewType==VIEW_FOOTER){
            return new BaseHolder(footerView);
        }else {
            return createCustomViewHolder(parent,viewType);
        }
    }

    public abstract VH createCustomViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(BaseHolder holder, final int position) {
        if (getItemViewType(position)==VIEW_HEADER||getItemViewType(position)==VIEW_FOOTER){
            return;
        }
        int itemType=holder.getItemViewType();
        switch (itemType){
//            case VIEW_HEADER:
//                break;
//            case VIEW_FOOTER:
//                break;
            default:
                bindCustomViewHolder((VH) holder,position);
                if (onItemClickListener!=null){
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onItemClickListener.onItemClick(v,position);
                        }
                    });
                }
                break;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position)==BaseRecyclerAdapter.VIEW_HEADER||
                            getItemViewType(position)==BaseRecyclerAdapter.VIEW_FOOTER?gridManager.getSpanCount():1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(BaseHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if(lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && holder.getLayoutPosition() == 0) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }

    public abstract void bindCustomViewHolder(VH holder, int position);

    @Override
    public int getItemCount() {
        int count=dataList.size();
        if (headerView!=null)
            count++;
        if (footerView!=null)
            count++;
        return count;
    }

    protected abstract int getCustomViewType(int position);

    public M getItem(int position){
        if (headerView!=null&&position==0
                ||position>=getExtraHeaderFooterItemCount()){
            return null;
        }
        return headerView==null?dataList.get(position):dataList.get(position-1);
    }

    @Override
    public int getItemViewType(int position) {
        if (headerView!=null&&position==0){
            return VIEW_HEADER;
        }else if (footerView!=null&&position==getExtraHeaderFooterItemCount()-1){
            return VIEW_FOOTER;
        }else {
            return getCustomViewType(position);
        }
    }

    public boolean isHeader(int position){
        return (hasHeader()&&position==0);
    }

    public boolean hasHeader(){
        return headerView!=null;
    }

    public int getExtraHeaderFooterItemCount(){
        int extraCount=dataList.size();
        if (headerView!=null){
            extraCount++;
        }
        if (footerView!=null){
            extraCount++;
        }
        return extraCount;
    }



    public void setHeaderView(View headerView){
        this.headerView=headerView;
        notifyItemInserted(0);
    }

    public void setFooterView(View footerView){
        this.footerView=footerView;
        notifyDataSetChanged();
    }

    public void removeHeaderView(){
        if (headerView!=null){
            headerView=null;
            notifyDataSetChanged();
        }
    }

    public void removeFooterView(){
        if (footerView!=null){
            footerView=null;
            notifyDataSetChanged();
        }
    }

    public void moveToPosition(){

    }
}
