package com.LianXiangKeJi.SupplyChain.setup.activity;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.LianXiangKeJi.SupplyChain.R;
import com.LianXiangKeJi.SupplyChain.address.activity.MyAddressActivity;
import com.LianXiangKeJi.SupplyChain.base.App;
import com.LianXiangKeJi.SupplyChain.base.BaseAvtivity;
import com.LianXiangKeJi.SupplyChain.base.BasePresenter;
import com.LianXiangKeJi.SupplyChain.base.Common;
import com.LianXiangKeJi.SupplyChain.bindphone.activity.ReplacePhoneActivity;
import com.LianXiangKeJi.SupplyChain.common.bean.GetPhoneCodeBean;
import com.LianXiangKeJi.SupplyChain.goodsdetails.activity.GoodsDetailsActivity;
import com.LianXiangKeJi.SupplyChain.main.activity.MainActivity;
import com.LianXiangKeJi.SupplyChain.order.activity.ConfirmOrderActivity;
import com.LianXiangKeJi.SupplyChain.rememberpwd.RememberPwdActivity;
import com.LianXiangKeJi.SupplyChain.setup.bean.UpdateImageBean;
import com.LianXiangKeJi.SupplyChain.setup.bean.UserNameBean;
import com.LianXiangKeJi.SupplyChain.utils.NetUtils;
import com.LianXiangKeJi.SupplyChain.utils.SPUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.wildma.pictureselector.PictureBean;
import com.wildma.pictureselector.PictureSelector;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Url;

/**
 * @ClassName:MyAddressAdapter
 * @Author:hmy
 * @Description:java类作用描述 设置页面
 */
public class SetUpActivity extends BaseAvtivity implements View.OnClickListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rl_myaddress)
    RelativeLayout rlMyaddress;
    @BindView(R.id.rl_changephone)
    RelativeLayout rlChangephone;
    @BindView(R.id.rl_changepwd)
    RelativeLayout rlChangepwd;
    @BindView(R.id.exit_login)
    Button exitLogin;
    @BindView(R.id.iv_touxiang)
    SimpleDraweeView ivTouxiang;
    @BindView(R.id.rl_mytouxiang)
    RelativeLayout rlMytouxiang;
    @BindView(R.id.tv_myname)
    TextView tvMyname;
    @BindView(R.id.rl_nicheng)
    RelativeLayout rlNicheng;
    private String token;
    private PopupWindow mPopupWindow;
    private String stringExtra;
    private String id;
    private File file;

    @Override
    protected int getResId() {
        return R.layout.activity_set_up;
    }

    @Override
    protected void getData() {
        token = Common.getToken();

        setTitleColor(this);
        back.setOnClickListener(this);
        tvRight.setVisibility(View.GONE);
        title.setText("设置");
        rlChangephone.setOnClickListener(this);
        rlChangepwd.setOnClickListener(this);
        rlMyaddress.setOnClickListener(this);
        exitLogin.setOnClickListener(this);
        rlMytouxiang.setOnClickListener(this);
        rlNicheng.setOnClickListener(this);

        String headurl = SPUtil.getInstance().getData(SetUpActivity.this, SPUtil.FILE_NAME, SPUtil.HEAD_URL);
        String username = SPUtil.getInstance().getData(SetUpActivity.this, SPUtil.FILE_NAME, SPUtil.USER_NAME);
        id = SPUtil.getInstance().getData(SetUpActivity.this, SPUtil.FILE_NAME, SPUtil.KEY_ID);

        Uri parse = Uri.parse(headurl);
        ivTouxiang.setImageURI(parse);

        tvMyname.setText(username);
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // TODO: 2020/7/18  更换手机号
            case R.id.rl_changephone:
                
                break;
            // TODO: 2020/7/18 修改密码
            case R.id.rl_changepwd:
                startActivity(new Intent(SetUpActivity.this, RememberPwdActivity.class));
                break;
            // TODO: 2020/7/18 返回
            case R.id.back:
                finish();
                break;
            // TODO: 2020/7/18 退出登录
            case R.id.exit_login:
                SPUtil.unReg(App.getContext(), SPUtil.FILE_NAME);
                startActivity(new Intent(SetUpActivity.this, MainActivity.class));
                finish();
                break;
            // TODO: 2020/7/18 我的地址
            case R.id.rl_myaddress:
                startActivity(new Intent(SetUpActivity.this, MyAddressActivity.class));
                break;
            // TODO: 2020/7/20 更换头像
            case R.id.rl_mytouxiang:
                PictureSelector.create(SetUpActivity.this, PictureSelector.SELECT_REQUEST_CODE).selectPicture(true, 200, 200, 200, 200);


                break;
            // TODO: 2020/7/20 修改昵称
            case R.id.rl_nicheng:
                showSelect();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK&&requestCode==PictureSelector.SELECT_REQUEST_CODE){
            if (data != null) {

                PictureBean bean = data.getParcelableExtra(PictureSelector.PICTURE_RESULT);
                stringExtra = bean.getPath();
                file = new File(stringExtra);

                ArrayList<File> list = new ArrayList<>();
                HashMap<String, String> map = new HashMap<>();
                map.put("id", id);
                list.add(file);
                RequestBody requsetBody = getRequsetBody(list, map);
                showDialog();
                NetUtils.getInstance().getApis().doUpdateHeadImage(requsetBody)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<UpdateImageBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(UpdateImageBean bean) {

                                hideDialog();
                                Toast.makeText(SetUpActivity.this, ""+bean.getData(), Toast.LENGTH_SHORT).show();
                                //发送到我的界面
                                EventBus.getDefault().post(file);
                                SPUtil.getInstance().saveData(SetUpActivity.this,SPUtil.FILE_NAME,SPUtil.HEAD_URL,file+"");

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                // TODO: 2020/7/22 将获取到的图片展示
                Glide.with(SetUpActivity.this).load(file).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivTouxiang);
            }
        }
    }

    // TODO: 2020/7/20 弹出选择规格
    public void showSelect() {
        //创建popwiondow弹出框
        mPopupWindow = new PopupWindow();
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_changename,null);
       ImageView ivclose =  view.findViewById(R.id.iv_close);
       EditText etname = view.findViewById(R.id.et_name);
       ImageView ivqingchu = view.findViewById(R.id.iv_qingchu);
       Button btsave = view.findViewById(R.id.bt_save);
       //将原昵称回显到弹出框
       etname.setText(tvMyname.getText().toString());
       ivclose.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               dismiss();
           }
       });
       ivqingchu.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               etname.setText("");
           }
       });
       btsave.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               // TODO: 2020/7/22 调用更换昵称接口
               //获取到当前输入框的文字
               String name = etname.getText().toString();
               if(TextUtils.isEmpty(name)){
                   Toast.makeText(SetUpActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
               }else{
                   UserNameBean userNameBean = new UserNameBean();
                   userNameBean.setId(id);
                   userNameBean.setUsername(name);

                   Gson gson = new Gson();
                   String json = gson.toJson(userNameBean);

                   RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                   showDialog();
                   NetUtils.getInstance().getApis().doUpdateUserName(requestBody)
                           .subscribeOn(Schedulers.io())
                           .observeOn(AndroidSchedulers.mainThread())
                           .subscribe(new Observer<UpdateImageBean>() {
                               @Override
                               public void onSubscribe(Disposable d) {

                               }

                               @Override
                               public void onNext(UpdateImageBean updateImageBean) {
                                   hideDialog();
                                   Toast.makeText(SetUpActivity.this, ""+updateImageBean.getData(), Toast.LENGTH_SHORT).show();
                                   EventBus.getDefault().post(name);
                                   SPUtil.getInstance().saveData(SetUpActivity.this,SPUtil.FILE_NAME,SPUtil.USER_NAME,name);
                               }

                               @Override
                               public void onError(Throwable e) {

                               }

                               @Override
                               public void onComplete() {

                               }
                           });



                   tvMyname.setText(name);
                   //添加完成后关闭弹出框
                   dismiss();
               }


           }
       });

        //popwindow设置属性
        mPopupWindow.setContentView(view);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpa(false);
            }
        });
        show(view);
    }

    // TODO: 2020/7/20 设置透明度
    public void setWindowAlpa(boolean isopen) {
        if (android.os.Build.VERSION.SDK_INT < 11) {
            return;
        }
        final Window window = this.getWindow();
        final WindowManager.LayoutParams lp = window.getAttributes();
        window.setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ValueAnimator animator;
        if (isopen) {
            animator = ValueAnimator.ofFloat(1.0f, 0.5f);
        } else {
            animator = ValueAnimator.ofFloat(0.5f, 1.0f);
        }
        animator.setDuration(400);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                lp.alpha = alpha;
                window.setAttributes(lp);
            }
        });
        animator.start();
    }

    /**
     * 显示PopupWindow
     */
    private void show(View v) {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        }
        setWindowAlpa(true);
    }


    /**
     * 消失PopupWindow
     */
    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }
    //xiangji
    public RequestBody getRequsetBody(List<File> files, HashMap<String,String> map){
//        if (map.size() < 1){
//            return null;
//        }
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        for (Map.Entry<String,String> entry:map.entrySet()){
            Log.i("xxx","key = "+entry.getKey()+"value = "+entry.getValue());
            builder.addFormDataPart(entry.getKey(),entry.getValue()+"");
        }

        builder.addFormDataPart("headPrint",files.get(0).getName(),RequestBody.create(MediaType.parse("image/jepg"),files.get(0)));

        return builder.build();
    }
}