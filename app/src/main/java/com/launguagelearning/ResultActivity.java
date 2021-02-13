package com.launguagelearning;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.launguagelearning.api.EndPointUrl;
import com.launguagelearning.api.RetrofitInstance;
import com.launguagelearning.model.ResponseData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultActivity extends BaseActivity {
    Button tv, tv2, tv3;
    Button btnRestart;
    PieChart piechart;
    int total = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Results");
        tv = (Button)findViewById(R.id.tvres);
        piechart = (PieChart)findViewById(R.id.piechart);
        tv2 = (Button)findViewById(R.id.tvres2);
        tv3 = (Button)findViewById(R.id.tvres3);
        btnRestart = (Button) findViewById(R.id.btnRestart);

        StringBuffer sb = new StringBuffer();
        sb.append("Correct answers: " + getIntent().getStringExtra("correct"));
        StringBuffer sb2 = new StringBuffer();
        sb2.append("Wrong Answers: " + getIntent().getStringExtra("wrong"));
        total=Integer.parseInt(getIntent().getStringExtra("correct"))+Integer.parseInt(getIntent().getStringExtra("wrong"));
        StringBuffer sb3 = new StringBuffer();
        sb3.append("Final Score: " + getIntent().getStringExtra("marks"));
        tv.setText(sb);
        tv2.setText(sb2);
        tv3.setText(sb3);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitdata();
            }
        });

        ArrayList NoOfEmp = new ArrayList();
        NoOfEmp.add(new Entry(Float.parseFloat( getIntent().getStringExtra("correct")), 0));
        piechart.setHoleColor(R.color.colorAccent);
        NoOfEmp.add(new Entry(Float.parseFloat( getIntent().getStringExtra("wrong")), 1));
        //   progress_bar.setProgress(QuestionsActivity.wrong);
        piechart.setHoleColor(R.color.colorPrimaryDark);

        PieDataSet dataSet = new PieDataSet(NoOfEmp, "");

        ArrayList year = new ArrayList();

        year.add("Correct");
        year.add("Wrong");

        PieData data = new PieData(year, dataSet);
        dataSet.setValueTextSize(13);
        data.setValueTextColor(Color.parseColor("#ffffff"));

        piechart.setData(data);
        // dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet .setColors(new int[] { Color.parseColor("#4CAF50"), Color.parseColor("#F44336")});
    }
    ProgressDialog pd;
    private void submitdata() {
        pd = new ProgressDialog(ResultActivity.this);
        pd.setTitle("Please wait,Data is being loaded...");
        pd.show();
        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        SharedPreferences sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        Call<ResponseData> call = apiService.add_results(sharedpreferences.getString("uname","-"),getIntent().getStringExtra("ctype"),getIntent().getStringExtra("level"),getIntent().getStringExtra("correct"),getIntent().getStringExtra("wrong"),""+total);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                if(response.body().status.equals("true")) {
                    Toast.makeText(ResultActivity.this,response.body().message , Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(ResultActivity.this,response.body().message , Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pd.dismiss();
            }
        });
    }
    @Override                                                                                                                    //add this method in your program
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
