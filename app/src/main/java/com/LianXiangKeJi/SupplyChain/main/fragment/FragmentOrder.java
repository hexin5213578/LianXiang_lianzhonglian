package com.LianXiangKeJi.SupplyChain.main.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseFragment;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.base.Common;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @ClassName:FragmentHome
 * @Author:hmy
 * @Description:java类作用描述
 */
public class FragmentOrder extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.tv_manager)
    TextView tvManager;
    @BindView(R.id.rc_order)
    RecyclerView rcOrder;
    @BindView(R.id.rb_checkAll)
    CheckBox rbCheckAll;
    @BindView(R.id.heji)
    TextView heji;
    @BindView(R.id.bt_jiesuan)
    Button btJiesuan;
    @BindView(R.id.tv_finish)
    TextView tvFinish;
    @BindView(R.id.bt_delete)
    Button btDelete;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.ll_jieSuan)
    RelativeLayout llJieSuan;
    @BindView(R.id.rl_noshopcar)
    RelativeLayout noshopcar;
    @BindView(R.id.ll_order)
    LinearLayout llOrder;
    private List<String>list = new ArrayList<>();
    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.fragment_order;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void getData() {
        String token = Common.getToken();
        if (!TextUtils.isEmpty(token)) {
            tvManager.setOnClickListener(this);
            tvFinish.setOnClickListener(this);
            btJiesuan.setOnClickListener(this);
            btDelete.setOnClickListener(this);
        } else {
            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();

        }
        // TODO: 2020/7/21 判断进货单数据是否为空 如果为空隐藏所有功能展示进货单为空图片
        if(list.size()>0&&list!=null){
            noshopcar.setVisibility(View.GONE);
            llOrder.setVisibility(View.VISIBLE);
        }else{
            noshopcar.setVisibility(View.VISIBLE);
            llOrder.setVisibility(View.GONE);
            tvManager.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_manager:
                tvManager.setVisibility(View.GONE);
                tvFinish.setVisibility(View.VISIBLE);
                heji.setVisibility(View.GONE);
                money.setVisibility(View.GONE);
                btJiesuan.setVisibility(View.GONE);
                btDelete.setVisibility(View.VISIBLE);

                break;
            case R.id.bt_jiesuan:
                // TODO: 2020/7/21 带数据去订单页发起结算

                break;
            case R.id.tv_finish:
                tvFinish.setVisibility(View.GONE);
                tvManager.setVisibility(View.VISIBLE);
                heji.setVisibility(View.VISIBLE);
                money.setVisibility(View.VISIBLE);
                btJiesuan.setVisibility(View.VISIBLE);
                btDelete.setVisibility(View.GONE);

                break;
            case R.id.bt_delete:
                // TODO: 2020/7/21 删除进货单中的数据

                break;
        }
    }
}
