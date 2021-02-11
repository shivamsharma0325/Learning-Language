package com.launguagelearning;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ColorsLearningDashboardActivity extends BaseActivity {
    Button btnLearning,btnTesting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors_learning_dashboard);
        getSupportActionBar().setTitle("Colors Dashboard");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnLearning =(Button)findViewById(R.id.btnLearning);
        btnTesting =(Button)findViewById(R.id.btnTesting);

        btnLearning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ColorsLearningActivity.class));
            }
        });
        btnTesting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ColorsLevelActivity.class));
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
