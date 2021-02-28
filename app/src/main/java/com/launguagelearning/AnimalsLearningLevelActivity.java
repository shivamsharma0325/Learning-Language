package com.launguagelearning;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.launguagelearning.api.EndPointUrl;
import com.launguagelearning.api.RetrofitInstance;
import com.launguagelearning.model.LevelsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimalsLearningLevelActivity extends AppCompatActivity {
    Button btnLevel1,btnLevel2,btnLevel3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals_learning_level);
        getSupportActionBar().setTitle("Animals - Levels");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnLevel1 =(Button)findViewById(R.id.btnLevel1);
        btnLevel2 =(Button)findViewById(R.id.btnLevel2);
        btnLevel3 =(Button)findViewById(R.id.btnLevel3);
        btnLevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), AnimalsLearningActivity.class);
                intent.putExtra("ctype","Animals");
                intent.putExtra("level","Level-1");
                startActivity(intent);
            }
        });
        btnLevel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), AnimalsLearningActivity.class);
                intent.putExtra("ctype","Animals");
                intent.putExtra("level","Level-2");
                startActivity(intent);
                /*SharedPreferences sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                getLevel1(sharedpreferences.getString("uname","-"),"Animals");*/

            }
        });
        btnLevel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), AnimalsLearningActivity.class);
                intent.putExtra("ctype","Animals");
                intent.putExtra("level","Level-3");
                startActivity(intent);
               /* SharedPreferences sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                getLevel2(sharedpreferences.getString("uname","-"),"Animals");*/

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
    ProgressDialog pd;
    List<LevelsModel> levels;
    private void getLevel1(String uname,String ctype) {
        pd = new ProgressDialog(AnimalsLearningLevelActivity.this);
        pd.setTitle("Please wait,Data is being loaded...");
        pd.show();
        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<LevelsModel>> call = apiService.getLevelsByUname(uname,ctype);
        call.enqueue(new Callback<List<LevelsModel>>() {
            @Override
            public void onResponse(Call<List<LevelsModel>> call, Response<List<LevelsModel>> response) {
                pd.dismiss();
                levels=response.body();
                if(levels!=null){
                    if(levels.size()>0) {
                        boolean isPresent=false;

                        for(int i=0;i<levels.size();i++){
                            if(levels.get(i).getLevel().equals("Level-1")){
                                isPresent = true;
                                break;
                            }
                        }
                        if(isPresent){
                            Intent intent=new Intent(getApplicationContext(), QuestionsActivity.class);
                            intent.putExtra("ctype",ctype);
                            intent.putExtra("level","Level-2");
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(),"Please complete Level-1",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Please complete Level-1",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Please complete Level-1",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<LevelsModel>> call, Throwable t) {
                pd.dismiss();
            }
        });
    }

    private void getLevel2(String uname,String ctype) {
        pd = new ProgressDialog(AnimalsLearningLevelActivity.this);
        pd.setTitle("Please wait,Data is being loaded...");
        pd.show();
        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<LevelsModel>> call = apiService.getLevelsByUname(uname,ctype);
        call.enqueue(new Callback<List<LevelsModel>>() {
            @Override
            public void onResponse(Call<List<LevelsModel>> call, Response<List<LevelsModel>> response) {
                pd.dismiss();
                levels=response.body();
                if(levels!=null){
                    if(levels.size()>0) {
                        boolean isPresent=false;

                        for(int i=0;i<levels.size();i++){
                            if(levels.get(i).getLevel().equals("Level-2")){
                                isPresent = true;
                                break;
                            }
                        }
                        if(isPresent){

                            Intent intent=new Intent(getApplicationContext(), QuestionsActivity.class);
                            intent.putExtra("ctype",ctype);
                            intent.putExtra("level","Level-3");
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(),"Please complete Level-2",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Please complete Level-2",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Please complete Level-2",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<LevelsModel>> call, Throwable t) {
                pd.dismiss();
            }
        });
    }
}