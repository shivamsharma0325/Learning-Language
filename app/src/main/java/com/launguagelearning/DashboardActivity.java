package com.launguagelearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.launguagelearning.api.EndPointUrl;
import com.launguagelearning.api.RetrofitInstance;
import com.launguagelearning.model.ModelTest;
import com.launguagelearning.model.ResultModel;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends BaseActivity {
Button btnAnimals,btnShapes,btnColors;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private DrawerLayout dl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().setTitle(getResources().getString(R.string.UserHomeScreen));
        navigationView();

        btnAnimals =(Button)findViewById(R.id.btnAnimals);
        btnShapes =(Button)findViewById(R.id.btnShapes);
        btnColors =(Button)findViewById(R.id.btnColors);
        btnAnimals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(getApplicationContext(),AnimalsLearningDashboardActivity.class));
            }
        });
        btnShapes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ShapesLearningDashboardActivity.class));
            }
        });
        btnColors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ColorsLearningDashboardActivity.class));
            }
        });
        //getResults();
    }
    private void navigationView(){
        dl = (DrawerLayout)findViewById(R.id.drawerlayout);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);
        dl.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.edit_profile:
                        startActivity(new Intent(getApplicationContext(),EditProfileActivity.class));
                        break;
                    case R.id.edit_result:
                        startActivity(new Intent(getApplicationContext(),GetResultActivity.class));
                        break;

                    case R.id.set_en:
                        setLocale("en");
                        restartActivity();
                        break;

                    case R.id.set_fr:
                        setLocale("fr");
                        restartActivity();
                        break;

                    case R.id.logout:
                        SharedPreferences sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("is_first", "yes");
                        editor.commit();
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        finish();
                        break;

                    default:
                        return true;
                }
                dl.closeDrawer(GravityCompat.START);
                return true;

            }
        });
    }
    @Override
    public void onBackPressed() {
        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        } else {
            dl.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

   /* public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.edit_profile:
                startActivity(new Intent(getApplicationContext(),EditProfileActivity.class));
                return true;
            case R.id.edit_result:
                startActivity(new Intent(getApplicationContext(),GetResultActivity.class));
                return true;

            case R.id.set_en:
                setLocale("en");
                restartActivity();
                return true;
            case R.id.set_fr:
                setLocale("fr");
                restartActivity();
                return true;
            case R.id.logout:
                SharedPreferences sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("is_first", "yes");
                editor.commit();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
                return true;
        }
        return (super.onOptionsItemSelected(item));
    }*/
    ProgressDialog pd;
    List<ResultModel> results;
    private void getResults() {
        SharedPreferences sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        pd = new ProgressDialog(DashboardActivity.this);
        pd.setTitle("Please wait,Data is being loaded...");
        pd.show();
        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<ResultModel>> call = apiService.getResults(sharedpreferences.getString("uname","-"));
        call.enqueue(new Callback<List<ResultModel>>() {
            @Override
            public void onResponse(Call<List<ResultModel>> call, Response<List<ResultModel>> response) {
                pd.dismiss();
                if (response.code()==200) {
                    if (response.body() != null) {
                        if(response.body().size()>0) {
                            results =response.body();
                            Toast.makeText(getApplicationContext(),""+response.body().size(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<ResultModel>> call, Throwable t) {
                pd.dismiss();
            }
        });
    }
    private void restartActivity() {
        Intent intent = getIntent();
        startActivity(intent);
        finish();
    }

}
