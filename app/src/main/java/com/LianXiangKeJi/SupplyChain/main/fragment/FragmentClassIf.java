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
import com.LianXiangKeJi.SupplyChain.main.bean.SaveSecondItemBean;
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
    private ClassifSearchGoodsAdapter adapter;
    private SecondListAdapter secondListAdapter;
    private List<ClassIfBean.DataBean> data;
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
        // 发起分类数据的请求
        BasePresenter basePresenter = getPresenter();
        if (basePresenter instanceof ClassIfPresenter) {
            showDialog();
            ((ClassIfPresenter) basePresenter).doGetClassIf();
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //首次进入加载第一条
        if(list.size()>0 && list!=null){
            ClassIfBean.DataBean dataBean = list.get(0);
            firstListAdapter.getId(0);
            firstListAdapter.notifyDataSetChanged();

            List<ClassIfBean.DataBean.ChildrenBean> children = dataBean.getChildren();
            manager = new GridLayoutManager(getContext(),3);
            rcSecondList.setLayoutManager(manager);
            secondListAdapter = new SecondListAdapter(getContext(),children);
            rcSecondList.setAdapter(secondListAdapter);
            //首次加载二级分类下三级分类的商品
            if(children.size()>0 && children!=null){
                secondListAdapter.getId(0);
                if(!TextUtils.isEmpty(token)){
                    getclassIfGoods(children.get(0).getId());
                }else{
                    getclassIfGoodsnoLogin(children.get(0).getId());
                }
            }
            secondListAdapter.notifyDataSetChanged();
        }
    }

    //接受一级列表条目点击
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getInt(Integer id) {
        //点击一级条目分类 获取二级条目分类
        firstListAdapter.getId(id);
        ClassIfBean.DataBean dataBean = list.get(id);
        List<ClassIfBean.DataBean.ChildrenBean> children = dataBean.getChildren();

        if(children.size()>0 && children!=null){
            String id1 = children.get(0).getId();

            if(!TextUtils.isEmpty(token)){
                getclassIfGoods(id1);
            }else{
                getclassIfGoodsnoLogin(id1);
            }
        }

        manager = new GridLayoutManager(getContext(),3);
        rcSecondList.setLayoutManager(manager);
        secondListAdapter = new SecondListAdapter(getContext(),children);
        rcSecondList.setAdapter(secondListAdapter);
        //刷新UI
        firstListAdapter.notifyDataSetChanged();
        secondListAdapter.notifyDataSetChanged();

    }
    //获取点击条目的id 查询id下的商品
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSearchID(SaveSecondItemBean bean){
        //点击二级条目获取三级条目下分类商品
        secondListAdapter.getId(bean.getIds());
        secondListAdapter.notifyDataSetChanged();

        if(!TextUtils.isEmpty(token)){
            getclassIfGoods(bean.getId());
        }else{
            getclassIfGoodsnoLogin(bean.getId());
        }
    }

    @Override
    public void onStart() {
        super.onStart();

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
        data = bean.getData();
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
            secondListAdapter = new SecondListAdapter(getContext(),children);
            rcSecondList.setAdapter(secondListAdapter);
            //首次加载二级分类下三级分类的商品
            if(children.size()>0 && children!=null){
                secondListAdapter.getId(0);
                if(!TextUtils.isEmpty(token)){
                    getclassIfGoods(children.get(0).getId());
                }else{
                    getclassIfGoodsnoLogin(children.get(0).getId());
                }
            }
            secondListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetClassError(String msg) {
        hideDialog();
        Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
    }

    //根据ID查询分类商品
    public void getclassIfGoods(String id){
        SaveIdBean saveIdBean = new SaveIdBean();
        saveIdBean.setId(id);
        Gson gson = new Gson();

        String json = gson.toJson(saveIdBean);

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

                            adapter = new ClassifSearchGoodsAdapter(getActivity(), dataBeans1);

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
                        List<List<ClassIfSearchGoodsNoLoginBean.DataBean>> data = bean.getData();

                        if(data.size()>0 &&data!=null){
                            for (int i =0;i<data.size();i++){
                                List<ClassIfSearchGoodsNoLoginBean.DataBean> dataBeans = data.get(i);
                                if(dataBeans.size()>0 && dataBeans!=null){
                                    LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
                                    rcSearchGoodss.setLayoutManager(manager);

                                    ClassifSearchGoodsNologinAdapter adapter = new ClassifSearchGoodsNologinAdapter(getContext(), dataBeans);

                                    rcSearchGoodss.setAdapter(adapter);
                                }
                            }
                        }


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
