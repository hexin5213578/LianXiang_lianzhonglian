package com.LianXiangKeJi.SupplyChain.protocol;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserAgreementActivity extends BaseAvtivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;

    @Override
    protected int getResId() {
        return R.layout.activity_user_agreement;
    }

    @Override
    protected void getData() {
        setTitleColor(UserAgreementActivity.this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvRight.setVisibility(View.GONE);
        title.setText("用户协议");
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
