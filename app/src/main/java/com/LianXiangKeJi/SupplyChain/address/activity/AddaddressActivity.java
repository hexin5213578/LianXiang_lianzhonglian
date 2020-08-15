package com.LianXiangKeJi.SupplyChain.address.activity;

import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.map.activity.MapActivity;
import com.amap.api.services.core.PoiItem;
import com.bigkoo.pickerview.OptionsPickerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AddaddressActivity extends BaseAvtivity implements View.OnClickListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.l3)
    RelativeLayout l3;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.Swich)
    Switch Swich;
    @BindView(R.id.tv_area)
    TextView tvArea;
    OptionsPickerView pvOptions;
    //  省份
    ArrayList<String> provinceBeanList = new ArrayList<>();
    //  城市
    ArrayList<String> cities;
    ArrayList<List<String>> cityList = new ArrayList<>();
    //  区/县
    ArrayList<String> district;
    ArrayList<List<String>> districts;
    ArrayList<List<List<String>>> districtList = new ArrayList<>();
    @BindView(R.id.rl_location)
    RelativeLayout rl_location;


    @Override
    protected int getResId() {
        return R.layout.activity_addaddress;
    }

    @Override
    protected void getData() {
        setTitleColor(this);

        back.setOnClickListener(this);
        tvRight.setText("保存");
        title.setText("添加收货地址");
        tvRight.setOnClickListener(this);
        rl_location.setOnClickListener(this);
        l3.setOnClickListener(this);
        Swich.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    // TODO: 2020/7/15 设为默认地址 
                } else {
                    // TODO: 2020/7/15 不做任何操作 
                }
            }
        });

        // TODO: 2020/7/15 获取省市县json数据
        //  创建选项选择器
        pvOptions = new OptionsPickerView(this);

        //  获取json数据
       // String province_data_json = JsonFileReader.getJson(this, "city_code.json");
        //  解析json数据
       // parseJson(province_data_json);

        //  设置三级联动效果
        pvOptions.setPicker(provinceBeanList, cityList, districtList, true);

        //  设置是否循环滚动
        pvOptions.setCyclic(false, false, false);

        // 设置默认选中的三级项目
        pvOptions.setSelectOptions(0, 0, 0);
        //  监听确定选择按钮
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String city = cityList.get(options1).get(option2);
                String address;
                if ("钓鱼岛".equals(city)) {
                    address = districtList.get(options1).get(option2).get(options3);
                } else if ("北京".equals(city) || "上海".equals(city) || "天津".equals(city) || "重庆".equals(city) || "澳门".equals(city) || "香港".equals(city)) {
                    //  如果是直辖市或者特别行政区只设置市和区/县
                    address = provinceBeanList.get(options1)
                            + " " + districtList.get(options1).get(option2).get(options3);
                } else {
                    address = provinceBeanList.get(options1)
                            + " " + cityList.get(options1).get(option2)
                            + " " + districtList.get(options1).get(option2).get(options3);
                }
                tvArea.setText(address);
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getAddress(PoiItem poiItem){
        etAddress.setText(poiItem.getSnippet()+poiItem.getTitle());
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
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
                // TODO: 2020/7/15 关闭此界面 
                finish();
                break;
            case R.id.tv_right:
                // TODO: 2020/7/15 点击保存拿到输入数据并发起网络请求 
                String name = etName.getText().toString();
                String phone = etPhone.getText().toString();
                String address = etAddress.getText().toString();
                String area = tvArea.getText().toString();

                // TODO: 2020/7/16 判斷數據 添加成功后跳轉至我的地址刷新UI展示已有地址

                Intent intent1 = new Intent(AddaddressActivity.this,MyAddressActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.l3:
                // TODO: 2020/7/15 弹出选项选择器 
                pvOptions.show();
                break;
            // TODO: 2020/7/16 定位
            case R.id.rl_location:
                Intent intent = new Intent(AddaddressActivity.this, MapActivity.class);
                startActivity(intent);
                break;
        }
    }

    //  解析json填充集合
    public void parseJson(String str) {
        try {
            //  获取json中的数组
            JSONArray jsonArray = new JSONArray(str);
            //  遍历数据组
            for (int i = 0; i < jsonArray.length(); i++) {
                //  获取省份的对象
                JSONObject provinceObject = jsonArray.optJSONObject(i);
                //  获取省份名称放入集合
                String provinceName = provinceObject.getString("name");  //name
                provinceBeanList.add(provinceName);
                //  获取城市数组
                JSONArray cityArray = provinceObject.getJSONArray("city");
                //JSONArray cityArray = provinceObject.optJSONArray("city"); //city
                cities = new ArrayList<>();//   声明存放城市的集合
                districts = new ArrayList<>();//声明存放区县集合的集合
                //  遍历城市数组
                for (int j = 0; j < cityArray.length(); j++) {
                    //  获取城市对象
                    JSONObject cityObject = cityArray.optJSONObject(j);
                    //  将城市放入集合
                    String cityName = cityObject.optString("name"); // name
                    cities.add(cityName);
                    district = new ArrayList<>();// 声明存放区县的集合
                    //  获取区县的数组

                    JSONArray areaArray = cityObject.optJSONArray("area"); //area
                    //  遍历区县数组，获取到区县名称并放入集合
                    for (int k = 0; k < areaArray.length(); k++) {
                        String areaName = areaArray.getString(k);
                        district.add(areaName);
                    }
                    //  将区县的集合放入集合
                    districts.add(district);
                }
                //  将存放区县集合的集合放入集合
                districtList.add(districts);
                //  将存放城市的集合放入集合
                cityList.add(cities);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}