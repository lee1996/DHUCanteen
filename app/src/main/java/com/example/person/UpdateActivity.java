package com.example.person;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.example.animutils.BaseActivity;
import com.example.customshoppingcardemo.MainActivity;
import com.example.customshoppingcardemo.R;

/**
 * Created by Leet on 2017/5/1 0001.
 */

public class UpdateActivity extends BaseActivity {
    private EditText name_update;
    private EditText age_update;
    private RadioGroup gender_update;
    private RadioButton male_update;
    private RadioButton female_update;
    private EditText weight_update;
    private RippleView update;
    private ImageView backtoperson;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private float needCal;
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
        setContentView(R.layout.update_page);
        name_update= (EditText) findViewById(R.id.name_update);
        age_update= (EditText) findViewById(R.id.age_update);
       gender_update= (RadioGroup) findViewById(R.id.gender_update);
        male_update= (RadioButton) findViewById(R.id.male_update);
        female_update= (RadioButton) findViewById(R.id.female_update);
        weight_update= (EditText) findViewById(R.id.weight_update);
        update= (RippleView) findViewById(R.id.update);
        name_update.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(name_update.getText().toString().length()==0||name_update.getText().toString().equals("")){
                    update.setEnabled(false);
                }else {
                    update.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        age_update.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(age_update.getText().toString().length()==0||age_update.getText().toString().equals("")){
                        update.setEnabled(false);
                    }else {
                        update.setEnabled(true);
                    }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        weight_update.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if(weight_update.getText().toString().length()!=0&&!weight_update.getText().toString().equals("")) {
                    update.setEnabled(true);
                    if (s.toString().contains(".")) {
                        if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                            s = s.toString().subSequence(0,
                                    s.toString().indexOf(".") + 3);
                            weight_update.setText(s);
                            weight_update.setSelection(s.length());
                        }
                    }
                    if (s.toString().trim().substring(0).equals(".")) {
                        s = "0" + s;
                        weight_update.setText(s);
                        weight_update.setSelection(2);
                    }

                    if (s.toString().startsWith("0")
                            && s.toString().trim().length() > 1) {
                        if (!s.toString().substring(1, 2).equals(".")) {
                            weight_update.setText(s.subSequence(0, 1));
                            weight_update.setSelection(1);
                            return;
                        }
                    }
                }else{
                    update.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        preferences=getSharedPreferences("userInfo",MODE_PRIVATE);
        name_update.setText(preferences.getString("username",""));
        age_update.setText(String.valueOf(preferences.getInt("age",10)));
        float init=(float) 23.5;
        String gender_set=preferences.getString("gender","男");
        if(gender_set.equals("男")){
            male_update.setChecked(true);
        }else{
            female_update.setChecked(true);
        }
        weight_update.setText(String.valueOf(preferences.getFloat("weight",init)));
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int age_value = Integer.parseInt(age_update.getText().toString());
                if (age_value < 18) {
                    Toast.makeText(UpdateActivity.this, "年龄需大于等于18！", Toast.LENGTH_SHORT).show();
                } else {
                    preferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                    editor = preferences.edit();
                    editor.putString("username", name_update.getText().toString());
                    editor.putInt("age", Integer.parseInt(age_update.getText().toString()));
                    String gender_value = null;
                    if (gender_update.getCheckedRadioButtonId() == R.id.male_update) {
                        gender_value = "男";
                    } else {
                        gender_value = "女";
                    }
                    editor.putString("gender", gender_value);
                    float weight_value = Float.parseFloat(weight_update.getText().toString());
                    editor.putFloat("weight", weight_value);
                    editor.commit();
                    if (gender_value.equals("女")) {
                        if (age_value >= 18 && age_value <= 30) {
                            needCal = (float) 14.6 * weight_value + 450;
                        } else if (age_value >= 31 && age_value <= 60) {
                            needCal = (float) 8.6 * weight_value + 830;
                        } else if (age_value >= 61) {
                            needCal = (float) 10.4 * weight_value + 600;
                        }
                    } else {
                        if (age_value >= 18 && age_value <= 30) {
                            needCal = (float) 15.2 * weight_value + 680;
                        } else if (age_value >= 31 && age_value <= 60) {
                            needCal = (float) 11.5 * weight_value + 830;
                        } else if (age_value >= 61) {
                            needCal = (float) 13.4 * weight_value + 490;
                        }
                    }
                    preferences=getSharedPreferences("needCal",MODE_PRIVATE);
                    editor=preferences.edit();
                    editor.putFloat("need",needCal);
                    editor.commit();
                    Toast.makeText(UpdateActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateActivity.this, PersonActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0,R.anim.out_to_left);
                    finish();
                }
            }
        });
        backtoperson= (ImageView) findViewById(R.id.backtoperson);
        backtoperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UpdateActivity.this,PersonActivity.class);
                startActivity(intent);
                overridePendingTransition(0,R.anim.out_to_left);
                finish();

            }
        });

    }

//    @Override
//    public void onBackPressed() {
//        Intent intent=new Intent(UpdateActivity.this,PersonActivity.class);
//        startActivity(intent);
//    }
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
//                    Intent intent=new Intent(UpdateActivity.this, PersonActivity.class);
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
