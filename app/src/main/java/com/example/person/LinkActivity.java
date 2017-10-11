package com.example.person;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.animutils.BaseActivity;
import com.example.customshoppingcardemo.MainActivity;
import com.example.customshoppingcardemo.R;

/**
 * Created by leet on 17-7-1.
 */

public class LinkActivity extends BaseActivity {
    private ImageView backtoperson;
    private static final int YSPEED_MIN=1000;
    private static final int XDISTANCE_MIN=50;
    private static final int YDISTANCE_MIN=100;
    private float xDown;
    private float yDown;
    private float xMove;
    private float yMove;
    private VelocityTracker mVelocityTracker;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.link);
        backtoperson= (ImageView) findViewById(R.id.backtoperson);
        backtoperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        overridePendingTransition(R.anim.leftin,R.anim.rightout);
//    }
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
//                    Intent intent=new Intent(LinkActivity.this, PersonActivity.class);
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
