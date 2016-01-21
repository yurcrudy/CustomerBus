package com.yurc.customerbus.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.yurc.customerbus.R;



/**
 * Date：1/21/2016
 * Author：yurc
 * Describe：
 */
public class WelcomeActivity extends BaseActivity implements Animation.AnimationListener{

    public static final int REQUESTCODE = 1;

    private ImageView iv_welcome;
    private Animation animation;
    private Bitmap mBitmap = null;
    private boolean FLAG = false;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if(FLAG){

            }else{

            }
            Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
            return false;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void initViews(){
        iv_welcome = (ImageView)findViewById(R.id.iv_welcome);
        animation = AnimationUtils.loadAnimation(this,R.anim.anim_welcome);

        iv_welcome.setImageResource(R.mipmap.welcome);
        animation.setFillEnabled(true); //启动Fill保持
        animation.setFillAfter(true);  //设置动画的最后一帧是保持在View上面
        iv_welcome.setAnimation(animation);
        animation.setAnimationListener(this);
    }



    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        handler.sendEmptyMessageDelayed(1,1000);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //在欢迎界面屏蔽BACK键
        if (keyCode == KeyEvent .KEYCODE_BACK) {
            return false;
        }
        return false;
    }
}
