package com.LianXiangKeJi.SupplyChain.main.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.common.custom.CustomCountView;
import com.LianXiangKeJi.SupplyChain.main.bean.ShopCarBean;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;

public class ShopcarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private List<ShopCarBean.DataBean> mShoppingCartList = new ArrayList<>();

    public ShopcarAdapter(Context context) {

        this.context = context;
    }

    public void setData(List<ShopCarBean.DataBean> list){
        mShoppingCartList = list;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_shop_car, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ShopCarBean.DataBean dataBean = mShoppingCartList.get(position);
        ((ViewHolder)holder).tvName.setText(dataBean.getName());
        ((ViewHolder)holder).tvGuige.setText(dataBean.getSpecs());
        ((ViewHolder)holder).tvPrice.setText("¥"+dataBean.getPrice());
        Glide.with(context).load(dataBean.getLittlePrintUrl()).into(((ViewHolder)holder).ivImage);


        //根据 bean 类中的是否选中，更改checkBox状态
        ((ViewHolder)holder).rbChecked.setChecked(dataBean.isPersonChecked());
        ((ViewHolder)holder).rbChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //必须是先赋值，再通知
                dataBean.setPersonChecked(isChecked);
                EventBus.getDefault().post("123");
            }
        });

        ((ViewHolder)holder).ccvItemShopCar.setOnCountChangedListener(new CustomCountView.OnCountChangedListener() {
            @Override
            public void onCountChanged(int count) {
                dataBean.setCount(count);
                EventBus.getDefault().post("123");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mShoppingCartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rb_checked)
        CheckBox rbChecked;
        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_guige)
        TextView tvGuige;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.ccv_item_shop_car)
        CustomCountView ccvItemShopCar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
