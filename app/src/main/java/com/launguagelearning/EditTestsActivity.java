package com.launguagelearning;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import com.launguagelearning.adapters.LessionsAdapter;
import com.launguagelearning.adapters.TestsAdapter;
import com.launguagelearning.api.EndPointUrl;
import com.launguagelearning.api.RetrofitInstance;
import com.launguagelearning.model.EditLessions;
import com.launguagelearning.model.ModelTest1;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTestsActivity extends AppCompatActivity {
ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tests);
        listView=(ListView)findViewById(R.id.listView);
        getSupportActionBar().setTitle("Edit Model Tests");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getTest();
    }
    ProgressDialog pd;
    List<ModelTest1> tests;
    private void getTest() {
        pd = new ProgressDialog(EditTestsActivity.this);
        pd.setTitle("Please wait,Data is being loaded...");
        pd.show();
        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<ModelTest1>> call = apiService.getAllTests();
        call.enqueue(new Callback<List<ModelTest1>>() {
            @Override
            public void onResponse(Call<List<ModelTest1>> call, Response<List<ModelTest1>> response) {
                pd.dismiss();
                tests=response.body();
                if(tests!=null){
                    if(tests.size()>0) {
                        listView.setAdapter(new TestsAdapter(EditTestsActivity.this,tests));
                    }else{
                        Toast.makeText(getApplicationContext(),"No Data Present",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"No Data Present",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<ModelTest1>> call, Throwable t) {
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