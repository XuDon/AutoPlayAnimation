package com.example.a13.autoplayanimation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener,View.OnClickListener{
    /*
     * 实现图片自动滚动，监听用户划动实现视图左右更换
     *          实现方法一： 利用计时器，定时向Handler发送消息，更换视图    gesture监听手势
     *          实现方法二： 使用AdapterViewFlipper组件，为其设置Adapter,通过按钮或手势调用
     *                          对应方法(showPrevious()、showNext()、stopFlipping())更换视图
     */
    //图片数组
    int []images = {R.mipmap.view1, R.mipmap.view2,
                     R.mipmap.view3, R.mipmap.view4, R.mipmap.view5};

    //添加单选按钮组与单选按钮
    RadioGroup radioGroup;

    //初始坐标
    int currentImgId=100;
    //初始化视图组件
    //ImageView imgView;

    GestureDetector gesture;
    MyAdapter myAdapter;
    AdapterViewFlipper viewFlipper;
    Button btn_pre,btn_play,btn_next;
    RadioButton[] radioButtons=new RadioButton[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_adapterview);

        bindViews();
//为AdapterViewFlipper设置适配器
        viewFlipper.setAdapter(myAdapter);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

               switch (checkedId){
                   case R.id.rb1:
                       myAdapter.imgView.setImageResource(images[0]);
                       break;
                   case R.id.rb2:
                       myAdapter.imgView.setImageResource(images[1]);
                       break;
                   case R.id.rb3:
                       myAdapter.imgView.setImageResource(images[2]);
                       break;
                   case R.id.rb4:
                       myAdapter.imgView.setImageResource(images[3]);
                       break;
                   case R.id.rb5:
                       myAdapter.imgView.setImageResource(images[4]);
                       break;
               }
            }
        });
//*****************************利用定时器向Handler发送消息切换图片**************************
//        //定义handler用于消息接收
//        final Handler handler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                if(msg.what==0x123){
//                    //图片轮播
//                    imgView.setImageResource(images[currentImgId++%images.length]);
//                }
//                super.handleMessage(msg);
//            }
//        };
//
//        //周期执行任务
//        TimerTask task  = new TimerTask() {
//            @Override
//            public void run() {
//                handler.sendEmptyMessage(0x123);
//            }
//        };
//        new Timer().schedule(task,3000,5000);
//***************************************************************************************************

    }

//将触屏事件交由gesture处理
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gesture.onTouchEvent(event);
    }
//************************************手势监听方法重写***************************************
    //按下手势
    @Override
    public boolean onDown(MotionEvent motionEvent) {
      //  Toast.makeText(MainActivity.this,"onDown()方法执行",Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
      //  Toast.makeText(MainActivity.this,"onShowPress()方法执行",Toast.LENGTH_SHORT).show();
    }
//抬起手势
    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
       // Toast.makeText(MainActivity.this,"onSingleTapUp()方法执行",Toast.LENGTH_SHORT).show();
        return false;
    }
//滚动手势
    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
      //  Toast.makeText(MainActivity.this,"onScroll()方法执行",Toast.LENGTH_SHORT).show();
        return false;
    }
//长按手势
    @Override
    public void onLongPress(MotionEvent motionEvent) {
        //若图片在滚动状态，长按界面图片停止滚动
        if(viewFlipper.isFlipping()){
            viewFlipper.stopFlipping();
        }
    }

//划动手势监听
    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        if(currentImgId<0||currentImgId>200) {
            currentImgId=100;
        }
        if(v>0){
            //imgView.setImageResource(images[(currentImgId -= 1) % images.length]);
            viewFlipper.showPrevious();
            viewFlipper.stopFlipping();
        }
        else{
            //imgView.setImageResource(images[(currentImgId += 1) % images.length]);
            viewFlipper.showNext();
            viewFlipper.stopFlipping();
        }
        return true;
    }

    //按钮点击事件监听
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.previous:
                //previous按钮
                viewFlipper.showPrevious();
                viewFlipper.stopFlipping();
                break;
            case R.id.next:
                //next按钮
                viewFlipper.showNext();
                viewFlipper.stopFlipping();
                break;
            case R.id.autoplay:
                //play按钮
                if(!viewFlipper.isFlipping()){
                    Toast.makeText(MainActivity.this,"1秒后我要开始切换了哦",Toast.LENGTH_SHORT).show();
                    viewFlipper.startFlipping();
                }
                else{
                    Toast.makeText(MainActivity.this,"已经在自动播放了哦",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void bindViews(){
        //imgView= (ImageView) findViewById(R.id.imageView);
        myAdapter = new MyAdapter(MainActivity.this,images,radioButtons);
        gesture = new GestureDetector(this,this);
        viewFlipper = (AdapterViewFlipper) findViewById(R.id.viewFlipper);
//activity_main的按钮
//        btn_pre= (Button) findViewById(R.id.btn_preview);
//        btn_play = (Button) findViewById(R.id.btn_stop);
//        btn_next = (Button) findViewById(R.id.btn_next);

//activity_adapterView的按钮
        btn_pre = (Button) findViewById(R.id.previous);
        btn_play = (Button) findViewById(R.id.autoplay);
        btn_next = (Button) findViewById(R.id.next);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioButtons[0] = (RadioButton) findViewById(R.id.rb1);
        radioButtons[1] = (RadioButton) findViewById(R.id.rb2);
        radioButtons[2] = (RadioButton) findViewById(R.id.rb3);
        radioButtons[3] = (RadioButton) findViewById(R.id.rb4);
        radioButtons[4] = (RadioButton) findViewById(R.id.rb5);

        btn_pre.setOnClickListener(this);
        btn_play.setOnClickListener(this);
        btn_next.setOnClickListener(this);

    }
//**************************************************************************************************

}
