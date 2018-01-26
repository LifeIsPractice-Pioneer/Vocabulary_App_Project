package com.example.pioneer.vocabulary_app_project.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pioneer.vocabulary_app_project.R;
import com.example.pioneer.vocabulary_app_project.launcher.LauncherActivity;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String APPID = "1106697318";
    private TextView know;
    private ImageButton wechat;
    private ImageButton qq;
    private ImageButton phone;
    private TextView pwd;
    private TextView protocol;
    //qq主操作对象
    private Tencent mTencent;
    //授权登录监听器
    private IUiListener loginListener;
    //获取用户信息监听器
    private IUiListener userInfoListener;
    //获取信息的范围参数
    private String scope;
    //qq用户信息
    private UserInfo userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findById();
        OnClickListener();
        initData();
    }

    //绑定事件
    public void findById() {
        know = (TextView) findViewById(R.id.login_tv_know);
        pwd = (TextView) findViewById(R.id.login_tv_pwd);
        protocol = (TextView) findViewById(R.id.login_tv_protocol);
        wechat = (ImageButton) findViewById(R.id.login_ib_wechat);
        qq = (ImageButton) findViewById(R.id.login_ib_qq);
        phone = (ImageButton) findViewById(R.id.login_ib_phone);
    }

    //绑定监听器
    public void OnClickListener() {
        know.setOnClickListener(this);
        pwd.setOnClickListener(this);
        protocol.setOnClickListener(this);
        wechat.setOnClickListener(this);
        qq.setOnClickListener(this);
        phone.setOnClickListener(this);
    }

    //点击操作
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_tv_know:
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, LauncherActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.login_ib_qq:
                login();
                userInfo = new UserInfo(LoginActivity.this, mTencent.getQQToken());
                userInfo.getUserInfo(userInfoListener);
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            default:
                break;

        }

    }

    private void initData() {
        //初始化qq主操作对象
        mTencent = Tencent.createInstance(APPID, LoginActivity.this);
        //申请所有权限
        scope = "all";
        loginListener = new IUiListener() {
            @Override
            public void onError(UiError arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onComplete(Object value) {
                // TODO Auto-generated method stub

                System.out.println("有数据返回..");
                if (value == null) {
                    return;
                }

                try {
                    JSONObject jo = (JSONObject) value;

                    int ret = jo.getInt("ret");

                    System.out.println("json=" + String.valueOf(jo));

                    if (ret == 0) {
                        Toast.makeText(LoginActivity.this, "登录成功",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                        String openID = jo.getString("openid");
                        String accessToken = jo.getString("access_token");
                        String expires = jo.getString("expires_in");
                        mTencent.setOpenId(openID);
                        mTencent.setAccessToken(accessToken, expires);
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                }

            }

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub

            }
        };

        userInfoListener = new IUiListener() {

            @Override
            public void onError(UiError arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onComplete(Object arg0) {
                // TODO Auto-generated method stub
                if (arg0 == null) {
                    return;
                }
                try {
                    JSONObject jo = (JSONObject) arg0;
                    int ret = jo.getInt("ret");
                    System.out.println("json=" + String.valueOf(jo));
                    String nickName = jo.getString("nickname");
                    String gender = jo.getString("gender");

                    Toast.makeText(LoginActivity.this, "你好，" + nickName,
                            Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    // TODO: handle exception
                }


            }

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub

            }
        };
    }

    private void login() {
        //如果session无效，就开始登录
        if (!mTencent.isSessionValid()) {
            //开始qq授权登录
            mTencent.login(LoginActivity.this, scope, loginListener);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Constants.REQUEST_LOGIN) {
                Tencent.handleResultData(data, loginListener);
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
        Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
    }

}
