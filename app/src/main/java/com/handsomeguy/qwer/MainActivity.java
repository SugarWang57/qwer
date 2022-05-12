package com.handsomeguy.qwer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    TextView main_goal;
    TextView main_done;

    MyDBHelper helper;
    SQLiteDatabase db;
    int goal;
    int size;
    int doneTime = 0;
    int timeToDrink;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new MyDBHelper(this,"sugar.db",null,1);
        db = helper.getWritableDatabase();
        main_done = findViewById(R.id.main_done);
        main_goal = findViewById(R.id.main_goal);

        Cursor cursor = db.query("info",null,null,null,null,null,null);
        while(cursor.moveToNext()){
            goal = cursor.getInt(cursor.getColumnIndex("goal"));
            size = cursor.getInt(cursor.getColumnIndex("size"));
            Log.i("sugar","goal:" + goal);
            Log.i("sugar","size:" + size);
        }
        if(goal / size != 0){
            timeToDrink = goal / size + 1;
        }
        timeToDrink = goal / size;
        main_goal.setText("距离目标还剩" + timeToDrink +"杯");
        main_done.setText("今天已喝"+ doneTime +"杯水");

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SettingActivity.class));
            }
        });

        findViewById(R.id.main_drink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timeToDrink > 0){
                    timeToDrink--;
                    doneTime++;
                    main_goal.setText("距离目标还剩" + timeToDrink +"杯");
                    main_done.setText("今天已喝"+ doneTime +"杯水");

                    if(timeToDrink == 1){
                        main_goal.setText("距离目标仅剩" + timeToDrink +"杯！！！");
                        main_done.setText("今天已喝"+ doneTime +"杯水");
                    }
                }

                if(timeToDrink == 0){
                    startActivity(new Intent(MainActivity.this,Done.class));
                    timeToDrink = -1;
                }
                if(timeToDrink == -1){
                    main_goal.setText("今天目标已经完成");
                    doneTime++;
                    main_done.setText("今天已喝"+ doneTime +"杯水");
                }



            }
        });
    }
}