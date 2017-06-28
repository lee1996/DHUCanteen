package com.example.welcome;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.example.customshoppingcardemo.MainActivity;
import com.example.customshoppingcardemo.R;

/**
 * Created by Leet on 2017/5/1 0001.
 */

public class WelcomeActivity extends Activity {
    private EditText username;
    private EditText age;
    private RadioGroup gender;
    private RadioButton male;
    private RadioButton female;
    private EditText weight;
    private RippleView submit;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private float needCal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);
        username= (EditText) findViewById(R.id.name);
        username.getBackground().setAlpha(125);
        age= (EditText) findViewById(R.id.age);
        age.getBackground().setAlpha(125);
        gender=(RadioGroup) findViewById(R.id.gender);
        LinearLayout genderbg= (LinearLayout) findViewById(R.id.gender_layout);
        genderbg.getBackground().setAlpha(125);
        male= (RadioButton) findViewById(R.id.male);
        female= (RadioButton) findViewById(R.id.female);
        weight= (EditText) findViewById(R.id.weight);
        submit= (RippleView) findViewById(R.id.submit);
        weight.getBackground().setAlpha(125);
        weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                    if (s.toString().contains(".")) {
                        if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                            s = s.toString().subSequence(0,
                                    s.toString().indexOf(".") + 3);
                            weight.setText(s);
                            weight.setSelection(s.length());
                        }
                    }
                    if (s.toString().trim().substring(0).equals(".")) {
                        s = "0" + s;
                        weight.setText(s);
                        weight.setSelection(2);
                    }

                    if (s.toString().startsWith("0")
                            && s.toString().trim().length() > 1) {
                        if (!s.toString().substring(1, 2).equals(".")) {
                            weight.setText(s.subSequence(0, 1));
                            weight.setSelection(1);
                            return;
                        }
                    }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((username.getText().toString().length()!=0&&!username.getText().toString().equals(""))&&(age.getText().toString().length()!=0&&!age.getText().toString().equals(""))
                        && (weight.getText().length()!=0&&!weight.getText().toString().equals(""))) {
                    int age_value = Integer.parseInt(age.getText().toString());
                    if (age_value < 18) {
                        Toast.makeText(WelcomeActivity.this, "年龄需大于等于18！", Toast.LENGTH_SHORT).show();
                    } else {
                        preferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                        editor = preferences.edit();
                        editor.putString("username", username.getText().toString());

                        editor.putInt("age", age_value);
                        String gender_value = null;
                        if (gender.getCheckedRadioButtonId() == R.id.male) {
                            gender_value = "男";
                        } else {
                            gender_value = "女";
                        }
                        editor.putString("gender", gender_value);
                        float weight_value = Float.parseFloat(weight.getText().toString());
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
                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    }else{
                        Toast.makeText(WelcomeActivity.this, "请完善信息！", Toast.LENGTH_SHORT).show();
                    }

            }
        });
        SharedPreferences share=this.getSharedPreferences("shared",MODE_PRIVATE);
        boolean isFirstRun=share.getBoolean("isFirstRun",true);
        SharedPreferences.Editor editor=share.edit();
        if(isFirstRun){
           // Toast.makeText(this,"是第一次运行",Toast.LENGTH_SHORT).show();
            editor.putBoolean("isFirstRun",false);
            editor.commit();
        }else{
            Intent intent=new Intent(WelcomeActivity.this,MainActivity.class);
            startActivity(intent);
            //Toast.makeText(this,"不是第一次运行",Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.floatin,R.anim.floatout);
    }
}
