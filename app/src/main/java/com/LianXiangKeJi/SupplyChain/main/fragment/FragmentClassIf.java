package com.LianXiangKeJi.SupplyChain.main.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseFragment;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.base.Common;
import com.LianXiangKeJi.SupplyChain.main.adapter.ClassifSearchGoodsAdapter;
import com.LianXiangKeJi.SupplyChain.main.adapter.ClassifSearchGoodsNologinAdapter;
import com.LianXiangKeJi.SupplyChain.main.adapter.FirstListAdapter;
import com.LianXiangKeJi.SupplyChain.main.adapter.SecondListAdapter;
import com.LianXiangKeJi.SupplyChain.main.bean.ClassIfBean;
import com.LianXiangKeJi.SupplyChain.main.bean.ClassIfSearchGoodsBean;
import com.LianXiangKeJi.SupplyChain.main.bean.ClassIfSearchGoodsNoLoginBean;
import com.LianXiangKeJi.SupplyChain.main.bean.SaveIdBean;
import com.LianXiangKeJi.SupplyChain.main.contract.ClassIfContract;
import com.LianXiangKeJi.SupplyChain.main.presenter.ClassIfPresenter;
import com.LianXiangKeJi.SupplyChain.search.activity.SearchActivity;
import com.LianXiangKeJi.SupplyChain.utils.NetUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

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
    @BindView(R.id.rc_search_goodss)
    RecyclerView rcSearchGoodss;
    private FirstListAdapter firstListAdapter;
    List<ClassIfBean.DataBean> list = new ArrayList<>();
    private GridLayoutManager manager;
    private String token;
    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
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
        token = Common.getToken();

        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
        // 发起分类的请求
        BasePresenter basePresenter = getPresenter();
        if (basePresenter instanceof ClassIfPresenter) {
            showDialog();
            ((ClassIfPresenter) basePresenter).doGetClassIf();
        }


    }
    //接受一级列表条目点击
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getInt(Integer id) {
        firstListAdapter.getId(id);

        ClassIfBean.DataBean dataBean = list.get(id);
        List<ClassIfBean.DataBean.ChildrenBean> children = dataBean.getChildren();

        manager = new GridLayoutManager(getContext(),3);
        rcSecondList.setLayoutManager(manager);
        SecondListAdapter secondListAdapter = new SecondListAdapter(getContext(),children);
        rcSecondList.setAdapter(secondListAdapter);

        //刷新UI
        firstListAdapter.notifyDataSetChanged();
        secondListAdapter.notifyDataSetChanged();


    }
    //获取点击条目的id 查询id下的商品
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSearchID(String id){
        if(!TextUtils.isEmpty(token)){
            getclassIfGoods(id);
        }else{
            getclassIfGoodsnoLogin(id);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        getclassIfGoods("4cbdb49a-f6d1-456c-bcc5-9e0d7b94b874797");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onGetClassIfSuccess(ClassIfBean bean) {
        hideDialog();
        List<ClassIfBean.DataBean> data = bean.getData();
        //将获取到的数据提成全局变量
        list.addAll(data);

        //布局管理器
        LinearLayoutManager manager1 = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcFirstList.setLayoutManager(manager1);
        //创建适配器
        firstListAdapter = new FirstListAdapter(getContext(), data);
        rcFirstList.setAdapter(firstListAdapter);



        //首次进入加载第一条
        ClassIfBean.DataBean dataBean = data.get(0);
        List<ClassIfBean.DataBean.ChildrenBean> children = dataBean.getChildren();
        manager = new GridLayoutManager(getContext(),3);
        rcSecondList.setLayoutManager(manager);
        SecondListAdapter secondListAdapter = new SecondListAdapter(getContext(),children);
        rcSecondList.setAdapter(secondListAdapter);

        secondListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetClassError(String msg) {

    }

    //根据ID查询分类商品
    public void getclassIfGoods(String id){
        SaveIdBean saveIdBean = new SaveIdBean();
        saveIdBean.setId(id);
        Gson gson = new Gson();

        String json = gson.toJson(saveIdBean);
        Log.d("hmy","json为"+json);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

        //调用查询接口
        NetUtils.getInstance().getApis().doClassifSearchGoods(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ClassIfSearchGoodsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ClassIfSearchGoodsBean classIfSearchGoodsBean) {

                        List<List<ClassIfSearchGoodsBean.DataBean>> data = classIfSearchGoodsBean.getData();


                        //处理返回的数据
                        if(data.size()>0 && data!=null){

                            List<ClassIfSearchGoodsBean.DataBean> dataBeans1 = data.get(0);

                            LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
                            rcSearchGoodss.setLayoutManager(manager);

                            ClassifSearchGoodsAdapter adapter = new ClassifSearchGoodsAdapter(getContext(), dataBeans1);

                            rcSearchGoodss.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideDialog();
                        if (e.getMessage().equals("timeout")){
                            Toast.makeText(getContext(), "连接超时", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    //根据ID查询分类商品
    public void getclassIfGoodsnoLogin(String id){
        SaveIdBean saveIdBean = new SaveIdBean();
        saveIdBean.setId(id);
        Gson gson = new Gson();

        String json = gson.toJson(saveIdBean);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

        //调用查询接口
        NetUtils.getInstance().getApis().doClassifSearchGoodsNoLogin(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ClassIfSearchGoodsNoLoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ClassIfSearchGoodsNoLoginBean bean) {
                        //处理返回的数据
                        List<ClassIfSearchGoodsNoLoginBean.DataBean> data = bean.getData();
                        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
                        rcSearchGoodss.setLayoutManager(manager);

                        ClassifSearchGoodsNologinAdapter adapter = new ClassifSearchGoodsNologinAdapter(getContext(), data);

                        rcSearchGoodss.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
