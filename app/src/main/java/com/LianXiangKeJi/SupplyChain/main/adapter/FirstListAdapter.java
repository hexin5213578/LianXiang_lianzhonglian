package com.LianXiangKeJi.SupplyChain.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.main.bean.ClassIfBean;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName:FirstListAdapter
 * @Author:hmy
 * @Description:java类作用描述
 */
public class FirstListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<ClassIfBean.DataBean.CategoryChildBean> list;
    private int id;

    public FirstListAdapter(Context context, List<ClassIfBean.DataBean.CategoryChildBean> list) {
        this.context = context;
        this.list = list;
    }
    public void getId(int ids){
        id = ids;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_firstlist, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(id ==position){
            ((ViewHolder)holder).rlFirst.setBackground(context.getResources().getDrawable(R.drawable.rl_first_bg));
            ((ViewHolder)holder).tvName.setTextColor(context.getResources().getColor(R.color.rc_selector_FontColor));
        }else{
            ((ViewHolder)holder).rlFirst.setBackground(context.getResources().getDrawable(R.drawable.rl_first_bg2));
            ((ViewHolder)holder).tvName.setTextColor(context.getResources().getColor(R.color.more_font));

        }
        ((ViewHolder) holder).tvName.setText(list.get(position).getName());
        ((ViewHolder) holder).rlFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.rl_first)
        LinearLayout rlFirst;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
