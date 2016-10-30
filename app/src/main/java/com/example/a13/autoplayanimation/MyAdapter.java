package com.example.a13.autoplayanimation;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by 13 on 2016/10/27.
 */

//自定义BaseAdapter
class MyAdapter extends BaseAdapter {

    public int [] res;      //获取资源数组
    private Context context; //获取上下文
    public ImageView imgView;
    public RadioButton[] radioButtons;



    MyAdapter(Context context,int[] res,RadioButton[]radioButtons){
        this.context=context;
        this.res = res;
        this.radioButtons = radioButtons;
        radioButtons = new RadioButton[res.length];

    }
    @Override
    public int getCount() {
        return res.length;
    }

    @Override
    public Object getItem(int i) {
        return res[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        imgView = new ImageView(context);
        imgView.setImageResource(res[i]);
        radioButtons[i].setChecked(true);
        imgView.setScaleType(ImageView.ScaleType.FIT_XY);

        imgView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        return imgView;
    }
}
