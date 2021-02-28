package com.launguagelearning;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.launguagelearning.adapters.LessionsAdapter;
import com.launguagelearning.api.EndPointUrl;
import com.launguagelearning.api.RetrofitInstance;
import com.launguagelearning.model.EditLessions;
import com.launguagelearning.model.LevelsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditLessionsActivity extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lessions);
        getSupportActionBar().setTitle("Edit Lessions");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView=(ListView)findViewById(R.id.listView);
        getLessions();

    }
    ProgressDialog pd;
    List<EditLessions> lessions;
    private void getLessions() {
        pd = new ProgressDialog(EditLessionsActivity.this);
        pd.setTitle("Please wait,Data is being loaded...");
        pd.show();
        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<EditLessions>> call = apiService.getAllLessions();
        call.enqueue(new Callback<List<EditLessions>>() {
            @Override
            public void onResponse(Call<List<EditLessions>> call, Response<List<EditLessions>> response) {
                pd.dismiss();
                lessions=response.body();
                if(lessions!=null){
                    if(lessions.size()>0) {
                        listView.setAdapter(new LessionsAdapter(EditLessionsActivity.this,lessions));
                    }else{
                        Toast.makeText(getApplicationContext(),"No Data Present",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"No Data Present",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<EditLessions>> call, Throwable t) {
                pd.dismiss();
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