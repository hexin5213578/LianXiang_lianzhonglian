package com.LianXiangKeJi.SupplyChain.map.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.address.activity.AddaddressActivity;
import com.amap.api.services.core.PoiItem;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ProjectName: LianXiang_lianzhonglian
 * @Package: com.LianXiangKeJi.SupplyChain.map.adapter
 * @ClassName: MapApapter
 * @Description: (java类作用描述)
 * @Author: 何梦洋
 * @CreateDate: 2020/7/16 19:52
 */
public class MapApapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private  Context context;
    private  ArrayList<PoiItem> list;


    public void setContext(Context con){
        this.context=con;
    }
    public void setData(ArrayList<PoiItem> list1){
        this.list = list1;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_map, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).tv.setText(list.get(position).getTitle());
        ((ViewHolder)holder).tv1.setText("河南省洛阳市"+list.get(position).getSnippet());
        ((ViewHolder) holder).ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PoiItem poiItem = list.get(position);
                EventBus.getDefault().post("河南省洛阳市"+list.get(position).getSnippet()+poiItem);
                EventBus.getDefault().post(1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv)
        TextView tv;
        @BindView(R.id.tv1)
        TextView tv1;
        @BindView(R.id.ll)
        LinearLayout ll;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
