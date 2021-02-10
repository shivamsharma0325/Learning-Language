package com.launguagelearning;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.launguagelearning.api.EndPointUrl;
import com.launguagelearning.api.RetrofitInstance;
import com.launguagelearning.model.ResponseData;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends BaseActivity {
    TextView tv_signup,tv_forgot_pwd;
    Button btnLogin;
    EditText editUserName,editPassword;
Spinner sp_role;
    SharedPreferences sharedpreferences;
    CheckBox chRememberMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");
        editUserName = (EditText) findViewById(R.id.editUserName);
        editPassword = (EditText) findViewById(R.id.editPassword);
        sp_role = (Spinner) findViewById(R.id.sp_role);

        tv_signup=(TextView)findViewById(R.id.tv_signup);
        tv_forgot_pwd=(TextView)findViewById(R.id.tv_forgot_pwd);
        chRememberMe=(CheckBox)findViewById(R.id.chRememberMe);
        btnLogin=(Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
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
                if(sp_role.getSelectedItem().toString().equals("User")) {
                    submitdata(editUserName.getText().toString(), editPassword.getText().toString());
                }else{
                     if(editUserName.getText().toString().equals("admin")&&editPassword.getText().toString().equals("123")) {
                         startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                         finish();
                     }else{
                         Toast.makeText(getApplicationContext(),"UserName/Password is invalid.",Toast.LENGTH_LONG).show();
                     }
                }
                /*int cnt = db.isUserAlreadyExist(editUserName.getText().toString(),editPassword.getText().toString());
                if(cnt!=0) {
                    Toast.makeText(getApplicationContext(),"Login is successfully.",Toast.LENGTH_LONG).show();
                    sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("is_first", "no");
                    editor.commit();
                    startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"UserName/Password is invalid.",Toast.LENGTH_LONG).show();
                }*/
            }
        });
        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
                startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
                finish();
            }
        });
        tv_forgot_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
            }
        });
    }
    ProgressDialog pd;
    private void submitdata(String uname,String pwd) {
        pd = new ProgressDialog(LoginActivity.this);
        pd.setTitle("Please wait,Data is being loaded...");
        pd.show();
        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<ResponseData> call = apiService.login(uname,pwd);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                if(response.body().status.equals("true")) {
                    Toast.makeText(getApplicationContext(),"Login is successfully.",Toast.LENGTH_LONG).show();
                    if(chRememberMe.isChecked()) {
                        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("is_first", "no");
                        editor.putString("uname", editUserName.getText().toString());

                        editor.commit();
                    }else{
                        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("uname", editUserName.getText().toString());
                        editor.commit();
                    }
                    startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"UserName/Password is invalid.",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pd.dismiss();
            }
        });
    }
}
