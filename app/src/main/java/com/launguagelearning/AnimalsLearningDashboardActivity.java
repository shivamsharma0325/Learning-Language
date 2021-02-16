package com.launguagelearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class AnimalsLearningDashboardActivity extends BaseActivity {
Button btnLearning,btnTesting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals_learning_dashboard);
        getSupportActionBar().setTitle("Animals Dashboard");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnLearning =(Button)findViewById(R.id.btnLearning);
        btnTesting =(Button)findViewById(R.id.btnTesting);

        btnLearning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AnimalsLearningActivity.class));
            }
        });
        btnTesting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AnimalsLevelActivity.class));
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
