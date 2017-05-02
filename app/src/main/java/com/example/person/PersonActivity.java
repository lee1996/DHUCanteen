package com.example.person;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.example.customshoppingcardemo.MainActivity;
import com.example.customshoppingcardemo.R;

import org.w3c.dom.Text;

/**
 * Created by Leet on 2017/5/1 0001.
 */

public class PersonActivity extends Activity {
    private TextView name_show;
    private TextView age_show;
    private TextView gender_show;
    private TextView weight_show;
    private ImageView backtomain;
    private RippleView change;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_page);
        change= (RippleView) findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PersonActivity.this,UpdateActivity.class);
                startActivity(intent);
            }
        });
        backtomain= (ImageView) findViewById(R.id.backtomain);
        backtomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PersonActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        name_show= (TextView) findViewById(R.id.name_show);
        age_show= (TextView) findViewById(R.id.age_show);
        gender_show= (TextView) findViewById(R.id.gender_show);
        weight_show= (TextView) findViewById(R.id.weight_show);
        preferences=getSharedPreferences("userInfo",MODE_PRIVATE);

        name_show.setText(preferences.getString("username","DHU"));
        age_show.setText(String.valueOf(preferences.getInt("age",10)));
        float init=(float) 23.5;
        gender_show.setText(preferences.getString("gender","男"));
        weight_show.setText(String.valueOf(preferences.getFloat("weight",init)));


    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(PersonActivity.this,MainActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.leftin,R.anim.rightout);
    }
}
