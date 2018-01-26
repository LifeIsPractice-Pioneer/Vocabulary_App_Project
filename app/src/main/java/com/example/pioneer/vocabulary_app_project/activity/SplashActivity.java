package com.example.pioneer.vocabulary_app_project.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.example.pioneer.vocabulary_app_project.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView imageView = (ImageView) findViewById(R.id.splash_iv_animation);
        //设置加载动画透明度渐变从(0.1不显示-1.0完全显示)
        AlphaAnimation animation = new AlphaAnimation(0.1f, 1.0f);
        //设置动画时间20s
        animation.setDuration(2000);
        //将组件与动画关联
        imageView.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            //动画开始时执行
            @Override
            public void onAnimationStart(Animation animation) {
            }

            //动画结束时执行
            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            }

            //动画重复时执行
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}
