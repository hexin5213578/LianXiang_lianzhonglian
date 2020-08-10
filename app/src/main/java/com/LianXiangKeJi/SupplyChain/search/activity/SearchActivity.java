package com.LianXiangKeJi.SupplyChain.search.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
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
import com.LianXiangKeJi.SupplyChain.base.App;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.base.Common;
import com.LianXiangKeJi.SupplyChain.search.adapter.SearchGoodsAdapter;
import com.LianXiangKeJi.SupplyChain.search.adapter.SearchGoodsNoLoginAdapter;
import com.LianXiangKeJi.SupplyChain.search.adapter.SearchHistoryAdapter;
import com.LianXiangKeJi.SupplyChain.search.bean.SaveKeywordBean;
import com.LianXiangKeJi.SupplyChain.search.bean.SearchGoodsBean;
import com.LianXiangKeJi.SupplyChain.search.bean.SearchGoodsNoLoginBean;
import com.LianXiangKeJi.SupplyChain.search.bean.SearchHotBean;
import com.LianXiangKeJi.SupplyChain.utils.NetUtils;
import com.LianXiangKeJi.SupplyChain.utils.SPUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.sv)
    SpringView sv;
    private List<String> list = new ArrayList<>();
    private SearchHistoryAdapter searchHistoryAdapter;
    private String token;
    List<String> list_searchhot  = new ArrayList<>();

    @Override
    protected int getResId() {
        return R.layout.activity_search;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册EventBus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void getData() {
        token = Common.getToken();

        //获取热门搜索
        NetUtils.getInstance().getApis().GetSearchHot()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchHotBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SearchHotBean searchHotBean) {
                        List<SearchHotBean.DataBean> data1 = searchHotBean.getData();
                        for (int i =0;i<data1.size();i++){
                            String name = data1.get(i).getName();
                            list_searchhot.add(name);
                        }
                        GridLayoutManager manager = new GridLayoutManager(SearchActivity.this, 4);
                        rcSearchHot.setLayoutManager(manager);
                        searchHistoryAdapter = new SearchHistoryAdapter(SearchActivity.this, list_searchhot);
                        rcSearchHot.setAdapter(searchHistoryAdapter);

                        //刷新适配器
                        searchHistoryAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        sv.setHeader(new DefaultHeader(SearchActivity.this));
        sv.setFooter(new DefaultFooter(SearchActivity.this));

        //上拉刷新下拉加载监听
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.onFinishFreshAndLoad();
                        if (!TextUtils.isEmpty(token)) {
                            doSearch("");
                        } else {
                            doSearchNoLogin("");
                        }
                    }
                }, 1000);
            }
            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.onFinishFreshAndLoad();
                    }
                }, 1000);
            }
        });
        setTitleColor(this);
        back.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        delete.setOnClickListener(this);

        rlHistory.setVisibility(View.VISIBLE);
        sv.setVisibility(View.GONE);

        rcSearchHistory.setVisibility(View.VISIBLE);
        rcSearchHot.setVisibility(View.VISIBLE);

        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rlHistory.setVisibility(View.VISIBLE);
                sv.setVisibility(View.GONE);
            }
        });
        if (!TextUtils.isEmpty(token)) {
            doSearch("");
        } else {
            doSearchNoLogin("");
        }

        //从sp取出
        Gson gson = new Gson();
        String data1 = SPUtil.getInstance().getData(SearchActivity.this, "searchlist", "search");
        if (data1 != null) {
            Type listType = new TypeToken<List<String>>() {
            }.getType();
            List<String> list_search = gson.fromJson(data1, listType);

            if (list_search.size() > 0 && list_search != null) {
                for (int i = 0; i < list_search.size(); i++) {
                    list.add(list_search.get(i));
                }
                //创建搜索历史记录适配器
                GridLayoutManager manager1 = new GridLayoutManager(SearchActivity.this, 4);
                rcSearchHistory.setLayoutManager(manager1);
                searchHistoryAdapter = new SearchHistoryAdapter(SearchActivity.this, list_search);
                rcSearchHistory.setAdapter(searchHistoryAdapter);
            }

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

                sv.setVisibility(View.VISIBLE);
                rlHistory.setVisibility(View.GONE);
                //拿到输入的内容
                String s = etSearch.getText().toString();
                etSearch.setText("");

                if (TextUtils.isEmpty(s)) {
                    Toast.makeText(this, "请输入搜索商品", Toast.LENGTH_SHORT).show();
                } else {
                    if (!TextUtils.isEmpty(token)) {
                        doSearch(s);
                    } else {
                        Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
                        etSearch.setText("");
                    }

                    for (int i = 0; i < list.size(); i++) {
                        if (s.equals(list.get(i))) {
                            return;
                        }
                    }
                    list.add(s);

                    Gson gson = new Gson();

                    String data = gson.toJson(list);

                    SPUtil.getInstance().saveData(SearchActivity.this, "searchlist", "search", data);


                    //从sp取出
                  /*  String data1 = SPUtil.getInstance().getData(SearchActivity.this, "searchlist", "search");
                    Type listType = new TypeToken<List<String>>() {
                    }.getType();
                    List<String> list_search = gson.fromJson(data1, listType);*/

                    //创建搜索历史记录适配器
                    GridLayoutManager manager = new GridLayoutManager(SearchActivity.this, 4);
                    rcSearchHistory.setLayoutManager(manager);
                    searchHistoryAdapter = new SearchHistoryAdapter(SearchActivity.this, list);
                    rcSearchHistory.setAdapter(searchHistoryAdapter);

                }

                break;
            case R.id.delete:
                //清除sp文件
                SPUtil.unReg(App.getContext(), "searchlist");
                list.clear();
                searchHistoryAdapter.notifyDataSetChanged();
                sv.setVisibility(View.VISIBLE);
                rlHistory.setVisibility(View.GONE);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getString(String str) {
        Log.d("hmy",str);
        sv.setVisibility(View.VISIBLE);
        rlHistory.setVisibility(View.GONE);
        closekeyboard();
        if (!TextUtils.isEmpty(token)) {
            doSearch(str);
        } else {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁EventBus
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    //  调用搜索接口

    /**
     * @param str 要搜索的内容
     */
    public void doSearch(String str) {
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

                        if (data.size() > 0 && data != null) {
                            LinearLayoutManager manager = new LinearLayoutManager(SearchActivity.this, RecyclerView.VERTICAL, false);
                            rcSearchGoods.setLayoutManager(manager);
                            SearchGoodsAdapter searchGoodsAdapter = new SearchGoodsAdapter(SearchActivity.this, data);
                            rcSearchGoods.setAdapter(searchGoodsAdapter);
                        } else {
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
     * @param str 未登录要搜索的内容
     */
    public void doSearchNoLogin(String str) {
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

                        if (data.size() > 0 && data != null) {
                            LinearLayoutManager manager = new LinearLayoutManager(SearchActivity.this, RecyclerView.VERTICAL, false);
                            rcSearchGoods.setLayoutManager(manager);
                            SearchGoodsNoLoginAdapter adapter = new SearchGoodsNoLoginAdapter(SearchActivity.this, data);
                            rcSearchGoods.setAdapter(adapter);
                        } else {
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