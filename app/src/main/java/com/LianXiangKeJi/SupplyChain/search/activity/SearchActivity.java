package com.LianXiangKeJi.SupplyChain.search.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.base.Common;
import com.LianXiangKeJi.SupplyChain.search.adapter.SearchGoodsAdapter;
import com.LianXiangKeJi.SupplyChain.search.adapter.SearchGoodsNoLoginAdapter;
import com.LianXiangKeJi.SupplyChain.search.adapter.SearchHistoryAdapter;
import com.LianXiangKeJi.SupplyChain.search.bean.SaveKeywordBean;
import com.LianXiangKeJi.SupplyChain.search.bean.SearchGoodsBean;
import com.LianXiangKeJi.SupplyChain.search.bean.SearchGoodsNoLoginBean;
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
 *
 */
public class SearchActivity extends BaseAvtivity implements View.OnClickListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.rc_search_goods)
    RecyclerView rcSearchGoods;
    @BindView(R.id.delete)
    ImageView delete;
    @BindView(R.id.rc_search_history)
    RecyclerView rcSearchHistory;
    @BindView(R.id.rc_search_hot)
    RecyclerView rcSearchHot;
    @BindView(R.id.rl_history)
    RelativeLayout rlHistory;
    private List<String> list = new ArrayList<>();
    private List<Integer> testlist = new ArrayList<>();
    private SearchHistoryAdapter searchHistoryAdapter;
    private String token;

    @Override
    protected int getResId() {
        return R.layout.activity_search;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册EventBus
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void getData() {
        setTitleColor(this);
        back.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        delete.setOnClickListener(this);
        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rlHistory.setVisibility(View.VISIBLE);
                rcSearchGoods.setVisibility(View.GONE);
            }
        });
        token = Common.getToken();
        if(!TextUtils.isEmpty(token)){
            doSearch("");
        }else{
            doSearchNoLogin("");
        }
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_search:
                closekeyboard();

                rcSearchGoods.setVisibility(View.VISIBLE);
                rlHistory.setVisibility(View.GONE);
                //拿到输入的内容
                String s = etSearch.getText().toString();
                etSearch.setText("");

                if(TextUtils.isEmpty(s)){
                    Toast.makeText(this, "请输入搜索商品", Toast.LENGTH_SHORT).show();
                }else{
                    if(!TextUtils.isEmpty(token)){
                        doSearch(s);
                    }else{
                        doSearchNoLogin(s);
                    }

                    for (int i=0;i<list.size();i++){
                        if(s.equals(list.get(i))){
                            return;
                        }
                    }
                    list.add(s);
                    //创建搜索历史记录适配器
                    GridLayoutManager manager = new GridLayoutManager(SearchActivity.this,4);
                    rcSearchHistory.setLayoutManager(manager);
                    searchHistoryAdapter = new SearchHistoryAdapter(SearchActivity.this, list);
                    rcSearchHistory.setAdapter(searchHistoryAdapter);
                }

                break;
            case R.id.delete:
                list.clear();
                searchHistoryAdapter.notifyDataSetChanged();
                rcSearchGoods.setVisibility(View.VISIBLE);
                rlHistory.setVisibility(View.GONE);
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getString (String str){
        rcSearchGoods.setVisibility(View.VISIBLE);
        rlHistory.setVisibility(View.GONE);
        closekeyboard();
        if(!TextUtils.isEmpty(token)){
            doSearch(str);
        }else{
            doSearchNoLogin(str);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁EventBus
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }


    // TODO: 2020/7/15 调用搜索接口

    /**
     *
     * @param str 要搜索的内容
     */
    public void doSearch(String str){
        SaveKeywordBean saveKeywordBean = new SaveKeywordBean();
        saveKeywordBean.setName(str);
        Gson gson = new Gson();
        String json = gson.toJson(saveKeywordBean);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        showDialog();
        NetUtils.getInstance().getApis().doSearchGoods(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchGoodsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SearchGoodsBean searchGoodsBean) {
                        hideDialog();
                        List<SearchGoodsBean.DataBean> data = searchGoodsBean.getData();

                        if(data.size()>0&&data!=null){
                            LinearLayoutManager manager = new LinearLayoutManager(SearchActivity.this,RecyclerView.VERTICAL,false);
                            rcSearchGoods.setLayoutManager(manager);
                            SearchGoodsAdapter searchGoodsAdapter = new SearchGoodsAdapter(SearchActivity.this,data);
                            rcSearchGoods.setAdapter(searchGoodsAdapter);
                        }else{
                            Toast.makeText(SearchActivity.this, "无搜索结果", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideDialog();
                        Toast.makeText(SearchActivity.this, "搜索失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     *
     * @param str 要搜索的内容
     */
    public void doSearchNoLogin(String str){
        SaveKeywordBean saveKeywordBean = new SaveKeywordBean();
        saveKeywordBean.setName(str);
        Gson gson = new Gson();
        String json = gson.toJson(saveKeywordBean);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        showDialog();
        NetUtils.getInstance().getApis().doSearchGoodsNoLogin(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchGoodsNoLoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SearchGoodsNoLoginBean bean) {
                        hideDialog();
                        List<SearchGoodsNoLoginBean.DataBean> data = bean.getData();

                        if(data.size()>0&&data!=null){
                            LinearLayoutManager manager = new LinearLayoutManager(SearchActivity.this,RecyclerView.VERTICAL,false);
                            rcSearchGoods.setLayoutManager(manager);
                            SearchGoodsNoLoginAdapter adapter = new SearchGoodsNoLoginAdapter(SearchActivity.this,data);
                            rcSearchGoods.setAdapter(adapter);
                        }else{
                            Toast.makeText(SearchActivity.this, "无搜索结果", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideDialog();
                        Toast.makeText(SearchActivity.this, "搜索失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}