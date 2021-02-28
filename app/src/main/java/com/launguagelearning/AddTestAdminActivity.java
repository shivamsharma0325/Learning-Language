package com.launguagelearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddTestAdminActivity extends BaseActivity {
    Button btnAnimals,btnShapes,btnColors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test_admin);
        btnAnimals =(Button)findViewById(R.id.btnAnimals);
        btnShapes=(Button)findViewById(R.id.btnShapes);
        btnColors=(Button)findViewById(R.id.btnColors);
        btnAnimals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddImageActivity.class));
            }
        });
        btnShapes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddImageActivity.class));
            }
        });
        btnColors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddImageActivity.class));
            }
        });
    }
}
