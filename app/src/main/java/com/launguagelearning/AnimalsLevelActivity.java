package com.launguagelearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class AnimalsLevelActivity extends AppCompatActivity {
Button btnLevel1,btnLevel2,btnLevel3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_level);
        getSupportActionBar().setTitle("Animals - Levels");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnLevel1 =(Button)findViewById(R.id.btnLevel1);
        btnLevel2 =(Button)findViewById(R.id.btnLevel2);
        btnLevel3 =(Button)findViewById(R.id.btnLevel3);
        btnLevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), QuestionsActivity.class);
                intent.putExtra("ctype","Animals");
                intent.putExtra("level","Level-1");
                startActivity(intent);
            }
        });
        btnLevel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), QuestionsActivity.class);
                intent.putExtra("ctype","Animals");
                intent.putExtra("level","Level-2");
                startActivity(intent);
            }
        });
        btnLevel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), QuestionsActivity.class);
                intent.putExtra("ctype","Animals");
                intent.putExtra("level","Level-3");
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
