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

public class PrivacypolicyActivity extends BaseAvtivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;

    @Override
    protected int getResId() {
        return R.layout.activity_privacy_policy;
    }

    @Override
    protected void getData() {
        setTitleColor(PrivacypolicyActivity.this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvRight.setVisibility(View.GONE);
        title.setText("隐私政策");
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

}
