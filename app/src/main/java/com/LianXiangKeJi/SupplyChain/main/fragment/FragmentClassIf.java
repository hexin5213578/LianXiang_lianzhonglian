package com.LianXiangKeJi.SupplyChain.main.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseFragment;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.main.adapter.FirstListAdapter;
import com.LianXiangKeJi.SupplyChain.main.bean.ClassIfBean;
import com.LianXiangKeJi.SupplyChain.main.contract.ClassIfContract;
import com.LianXiangKeJi.SupplyChain.main.presenter.ClassIfPresenter;
import com.LianXiangKeJi.SupplyChain.search.activity.SearchActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName:FragmentHome
 * @Author:hmy
 * @Description:java类作用描述
 */
public class FragmentClassIf extends BaseFragment implements ClassIfContract.IView {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.rc_firstList)
    RecyclerView rcFirstList;
    @BindView(R.id.rc_secondList)
    RecyclerView rcSecondList;
    private FirstListAdapter firstListAdapter;

    @Override
    public void onResume() {
        super.onResume();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.fragment_classif;
    }

    @Override
    protected BasePresenter initPresenter() {
        return new ClassIfPresenter(this);
    }

    @Override
    protected void getData() {
        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
        // 发起分类的请求
        BasePresenter basePresenter = getPresenter();
        if(basePresenter instanceof ClassIfPresenter){
            showDialog();
            ((ClassIfPresenter) basePresenter).doGetClassIf();
        }


    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getInt(Integer id){
        firstListAdapter.getId(id);
        firstListAdapter.notifyDataSetChanged();



    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onGetClassIfSuccess(ClassIfBean bean) {
        hideDialog();
        List<ClassIfBean.DataBean> data = bean.getData();
        //布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        rcFirstList.setLayoutManager(manager);
        //创建适配器
        firstListAdapter = new FirstListAdapter(getContext(), data);
        rcFirstList.setAdapter(firstListAdapter);
    }

    @Override
    public void onGetClassError(String msg) {

    }
}
