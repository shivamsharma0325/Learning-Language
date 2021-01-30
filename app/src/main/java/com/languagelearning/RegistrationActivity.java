package com.languagelearning;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.languagelearning.api.EndPointUrl;
import com.languagelearning.api.RetrofitInstance;
import com.languagelearning.model.ResponseData;

import androidx.appcompat.app.AppCompatActivity;
import api.EndPointUrl;
import api.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistrationActivity extends AppCompatActivity {
    EditText editFirstName,editLastName,editUserName,editPassword,editConfirmPassword;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().setTitle("Registration");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editFirstName = (EditText)findViewById(R.id.editFirstName);
        editLastName = (EditText)findViewById(R.id.editLastName);
        editUserName = (EditText)findViewById(R.id.editUserName);
        editPassword = (EditText)findViewById(R.id.editPassword);
        editConfirmPassword = (EditText)findViewById(R.id.editConfirmPassword);
        btnSubmit =(Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editFirstName.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please enter first name",Toast.LENGTH_LONG).show();
                    return;
                }
                if(editLastName.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please enter last name",Toast.LENGTH_LONG).show();
                    return;
                }
                if(editUserName.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please enter email id",Toast.LENGTH_LONG).show();
                    return;
                }
                if(editPassword.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please enter password",Toast.LENGTH_LONG).show();
                    return;
                }
                if(editConfirmPassword.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please enter confirm password",Toast.LENGTH_LONG).show();
                    return;
                }
                if(!editPassword.getText().toString().equals(editConfirmPassword.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(),"Password and Confirm Password should be same.",Toast.LENGTH_LONG).show();
                    return;
                }
                submitdata(editFirstName.getText().toString(),editLastName.getText().toString(),editUserName.getText().toString(),editPassword.getText().toString());
              /*  int cnt = db.isUserAlreadyExist(editUserName.getText().toString(),editPassword.getText().toString());
                if(cnt==0) {
                    db.addUser(new User(editUserName.getText().toString(), editPassword.getText().toString()));
                    Toast.makeText(getApplicationContext(),"UserName is added successfully.",Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"UserName already exist.",Toast.LENGTH_LONG).show();
                }*/
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
    ProgressDialog pd;
    private void submitdata(String fname,String lname,String uname,String pwd) {
        pd = new ProgressDialog(RegistrationActivity.this);
        pd.setTitle("Please wait,Data is being loaded...");
        pd.show();
        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<ResponseData> call = apiService.registration(fname,lname,uname,pwd);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                if(response.body().status.equals("true")) {
                    Toast.makeText(RegistrationActivity.this,response.body().message , Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(RegistrationActivity.this,response.body().message , Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pd.dismiss();
            }
        });
    }
}