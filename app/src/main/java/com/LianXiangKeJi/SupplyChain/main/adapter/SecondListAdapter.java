package com.LianXiangKeJi.SupplyChain.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.main.bean.ClassIfBean;

import org.greenrobot.eventbus.EventBus;

import java.util.EventListener;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName:SecondListAdapter
 * @Author:hmy
 * @Description:java类作用描述 分类详情列表
 */
public class SecondListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<ClassIfBean.DataBean.ChildrenBean> children;


    public SecondListAdapter(Context context, List<ClassIfBean.DataBean.ChildrenBean> children) {

        this.context = context;
        this.children = children;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_classif_second_list, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).tvName.setText(children.get(position).getName());
        ((ViewHolder)holder).tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击具体条目发送到fragment 做ID检索
                EventBus.getDefault().post(children.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return children.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
