package com.languagelearning;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.launguagelearning.db.DatabaseHandler;
import com.launguagelearning.model.User;


public class RegistrationActivity extends AppCompatActivity {
    EditText editUserName,editPassword;
    Button btnSubmit;
    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().setTitle("Registration");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new DatabaseHandler(this);
        editUserName = (EditText)findViewById(R.id.editUserName);
        editPassword = (EditText)findViewById(R.id.editPassword);
        btnSubmit =(Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editUserName.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please enter username",Toast.LENGTH_LONG).show();
                    return;
                }
                if(editPassword.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please enter password",Toast.LENGTH_LONG).show();
                    return;
                }
                int cnt = db.isUserAlreadyExist(editUserName.getText().toString(),editPassword.getText().toString());
                if(cnt==0) {
                    db.addUser(new User(editUserName.getText().toString(), editPassword.getText().toString()));
                    Toast.makeText(getApplicationContext(),"UserName is added successfully.",Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"UserName already exist.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}