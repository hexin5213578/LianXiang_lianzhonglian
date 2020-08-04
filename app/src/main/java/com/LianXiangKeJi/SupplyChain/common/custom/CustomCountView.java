package com.LianXiangKeJi.SupplyChain.common.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;

import com.LianXiangKeJi.SupplyChain.R;

import org.greenrobot.eventbus.EventBus;

/**
 * 自定义view实现加减器
 * 1.继承LinearLayout
 * 2.加载布局
 * 3.给加号和减号添加点击事件
 * 4.先取出原来的数量，转成整型，建议使用try catch 包裹，避免转换异常，记录一下数量
 * 5.对加号的点击事件把数量+1，我这用了 ++count 也可以用 count+1，不能用 count++ ；
 * 6.减法的时候，我们需要注意，数量减完了不能 <=0 ,也就是原来的数量必须大于1，才能执行减法
 * 7.我的加减器数量发生变化的时候，必须要先修改 Bean 类里面的数量，然后才能通知 Activity 计算
 *   但是我们的加减器里面没有 Bean 类，也没有下标， 所以我们要通知 adapter 改变集合里面的数据
 *   所以，我们使用接口回调的方式，通知 adapter
 */
public class CustomCountView extends LinearLayout implements View.OnClickListener {
    TextView mTvCount;
    Context mContext;

    public CustomCountView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public CustomCountView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init(){
        View view = View.inflate(mContext, R.layout.view_count, null);
        mTvCount = view.findViewById(R.id.tv_view_count);
        view.findViewById(R.id.tv_view_count_reduce).setOnClickListener(this);
        view.findViewById(R.id.tv_view_count_add).setOnClickListener(this);
        addView(view);
    }

    public void setCount(int count){
        if(count <= 0){
            return;
        }
        mTvCount.setText(String.valueOf(count));
    }

    private OnCountChangedListener mOnCountChangedListener;

    public void setOnCountChangedListener(OnCountChangedListener onCountChangedListener){
        mOnCountChangedListener = onCountChangedListener;
    }

    public interface OnCountChangedListener{
        void onCountChanged(int count);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        // 记录取出来的数量，先给一个默认值
        int count = 0;
        try {
            // 取出真正的数量，并且转换成整型
            count = Integer.valueOf(mTvCount.getText().toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        switch (id){
            case R.id.tv_view_count_add:
                // 如果使用 count++ ，则系统会优先使用原来的 count，然后做++处理，也就是，数量其实没有变
                // 使用 ++count, 系统会先++，然后在赋值，也就是实际上 +1 了
                setCount(++count);
                onCallBack(count);
                break;
            case R.id.tv_view_count_reduce:
                // 如果取出的数量 小于等于 1，则点击减号给出提示，并且返回
                if(count <= 1){
                    Toast.makeText(mContext, "数量不能为0", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 如果大于1，使用 --count，理由，同++count
                setCount(--count);
                onCallBack(count);
                break;
            default:
                break;
        }
    }

    private void onCallBack(int count){
        if(mOnCountChangedListener != null){
            mOnCountChangedListener.onCountChanged(count);
        }
    }
}
