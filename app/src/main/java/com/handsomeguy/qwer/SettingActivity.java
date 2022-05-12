package com.handsomeguy.qwer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {

    MyDBHelper helper;
    SQLiteDatabase db;
    int goal = 1500;
    int size = 250;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        helper = new MyDBHelper(this,"sugar.db",null,1);
        SQLiteDatabase db = helper.getWritableDatabase();

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(SettingActivity.this,MainActivity.class));
            }
        });

        findViewById(R.id.btn_insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText1 =(EditText) findViewById (R.id.goal);
                goal =Integer.parseInt(editText1.getText().toString());

                EditText editText2 =(EditText) findViewById (R.id.size);
                size =Integer.parseInt(editText2.getText().toString());
                db.execSQL("INSERT INTO info(goal,size) VALUES(?,?)",new Object[]{goal,size});
            }
        });

        findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = db.query("info",null,null,null,null,null,null);
                while(cursor.moveToNext()){
                    @SuppressLint("Range")
                    int goal = cursor.getInt(cursor.getColumnIndex("goal"));
                    @SuppressLint("Range")
                    int size = cursor.getInt(cursor.getColumnIndex("size"));
                    Log.i("sugar","goal:" + goal);
                    Log.i("sugar","size:" + size);
                    TextView disp = findViewById(R.id.disp);
                    disp.setText("目标饮水量：" + goal + "水杯大小：" + size);
                }
            }
        });
    }

}