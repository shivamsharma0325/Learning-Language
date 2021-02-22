package com.launguagelearning;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.launguagelearning.R;
import com.launguagelearning.api.EndPointUrl;
import com.launguagelearning.api.RetrofitInstance;
import com.launguagelearning.model.ResultModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetResultActivity extends BaseActivity {
    ListView list_view;
    ProgressDialog pd;
    List<ResultModel> results;
    SharedPreferences sharedPreferences;
    ImageButton ibLevel1,ibLevel2,ibLevel3;
    RadioButton rbAnimals,rbShapes,rbColors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_result);
        getSupportActionBar().setTitle("Results");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list_view=(ListView)findViewById(R.id.list_view);
        rbAnimals=(RadioButton)findViewById(R.id.rbAnimals);
        rbShapes=(RadioButton)findViewById(R.id.rbShapes);
        rbColors=(RadioButton)findViewById(R.id.rbColors);

        ibLevel1=(ImageButton)findViewById(R.id.ibLevel1);
        ibLevel2=(ImageButton)findViewById(R.id.ibLevel2);
        ibLevel3=(ImageButton)findViewById(R.id.ibLevel3);

        results= new ArrayList<>();
        ibLevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getResults("Level-1");
            }
        });
        ibLevel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getResults("Level-2");
            }
        });
        ibLevel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getResults("Level-3");
            }
        });
        getResults("Level-1");
    }
String ctype="Animals",level="";
    private void getResults(String level) {
        if(rbAnimals.isChecked()){
            ctype="Animals";
        }else if(rbShapes.isChecked()){
            ctype="Shapes";
        }else{
            ctype="Colors";
        }
        SharedPreferences sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        pd = new ProgressDialog(GetResultActivity.this);
        pd.setTitle("Please wait,Data is being loaded...");
        pd.show();
        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<ResultModel>> call = apiService.getResults(sharedpreferences.getString("uname","-"),ctype,level);
        call.enqueue(new Callback<List<ResultModel>>() {
            @Override
            public void onResponse(Call<List<ResultModel>> call, Response<List<ResultModel>> response) {
                pd.dismiss();
                if (response.code()==200) {
                    if (response.body() != null) {
                        if(response.body().size()>0) {
                            results =response.body();
                            list_view.setAdapter(new GetResultAdapter(results, GetResultActivity.this));
                            //Toast.makeText(getApplicationContext(),""+response.body().size(),Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"No data found",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"No data found",Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<List<ResultModel>> call, Throwable t) {
                pd.dismiss();
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