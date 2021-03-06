package com.example.person;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.example.animutils.BaseActivity;
import com.example.customshoppingcardemo.MainActivity;
import com.example.customshoppingcardemo.R;

import org.w3c.dom.Text;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Leet on 2017/5/1 0001.
 */

public class PersonActivity extends BaseActivity {
    @InjectView(R.id.change)
    RippleView change;
    @InjectView(R.id.backtomain)
    ImageView backtomain;
    @InjectView(R.id.name_show)
    TextView name_show;
    @InjectView(R.id.age_show)
    TextView age_show;
    @InjectView(R.id.gender_show)
    TextView gender_show;
    @InjectView(R.id.weight_show)
    TextView weight_show;
    @InjectView(R.id.link)
    ImageView link;
    private SharedPreferences preferences;
//    private static final int YSPEED_MIN=1000;
//    private static final int XDISTANCE_MIN=50;
//    private static final int YDISTANCE_MIN=100;
//    private float xDown;
//    private float yDown;
//    private float xMove;
//    private float yMove;
//    private VelocityTracker mVelocityTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_page);
        ButterKnife.inject(PersonActivity.this);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PersonActivity.this,UpdateActivity.class);
                startActivity(intent);
                overridePendingTransition(0,R.anim.out_from_right);
                finish();

            }
        });
        backtomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PersonActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0,R.anim.out_to_left);
                finish();

            }
        });
        preferences=getSharedPreferences("userInfo",MODE_PRIVATE);

        name_show.setText(preferences.getString("username","DHU"));
        age_show.setText(String.valueOf(preferences.getInt("age",10)));
        float init=(float) 23.5;
        gender_show.setText(preferences.getString("gender","男"));
        weight_show.setText(String.valueOf(preferences.getFloat("weight",init)));
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PersonActivity.this,LinkActivity.class);
                startActivity(intent);


            }
        });

    }

//    @Override
//    public void onBackPressed() {
//        Intent intent=new Intent(PersonActivity.this,MainActivity.class);
//        startActivity(intent);
//
//    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        overridePendingTransition(R.anim.leftin,R.anim.rightout);
//   }
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        createVelocity(ev);
//        switch (ev.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                xDown=ev.getRawX();
//                yDown=ev.getRawY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                xMove=ev.getRawX();
//                yMove=ev.getRawY();
//                int distanceX=(int)(xMove-xDown);
//                int distanceY=(int)(yMove-yDown);
//                int ySpeed=getScrollVelocity();
//                if(distanceX > XDISTANCE_MIN &&(distanceY<YDISTANCE_MIN&&distanceY>-YDISTANCE_MIN)&& ySpeed < YSPEED_MIN) {
//                    Intent intent=new Intent(PersonActivity.this, MainActivity.class);
//                    startActivity(intent);
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                recycleVelocityTracker();
//                break;
//            default:
//                break;
//        }
//        return super.dispatchTouchEvent(ev);
//    }
//    public void createVelocity(MotionEvent event){
//        if(mVelocityTracker==null){
//            mVelocityTracker=VelocityTracker.obtain();
//        }
//        mVelocityTracker.addMovement(event);
//    }
//
//    private void recycleVelocityTracker(){
//        mVelocityTracker.recycle();
//        mVelocityTracker=null;
//    }
//
//    private int getScrollVelocity(){
//        mVelocityTracker.computeCurrentVelocity(1000);
//        int velocity=(int)mVelocityTracker.getYVelocity();
//        return velocity;
//    }
}
