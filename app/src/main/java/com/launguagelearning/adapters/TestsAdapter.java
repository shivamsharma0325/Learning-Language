package com.launguagelearning.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.launguagelearning.EditLessionDetailsActivity;
import com.launguagelearning.EditModelTestsActivity;
import com.launguagelearning.R;
import com.launguagelearning.api.EndPointUrl;
import com.launguagelearning.api.RetrofitInstance;
import com.launguagelearning.model.EditLessions;
import com.launguagelearning.model.ModelTest1;
import com.launguagelearning.model.ResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TestsAdapter extends BaseAdapter {
    Context context;
    List<ModelTest1> lessions;
    LayoutInflater inflter;
    Button btnEdit,btnDelete;

    public TestsAdapter(Context context, List<ModelTest1> lessions) {
        this.context = context;
        this.lessions = lessions;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return lessions.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.row_lessions, null);
        TextView tvName = (TextView)view.findViewById(R.id.tvName);
        TextView tvOptions = (TextView)view.findViewById(R.id.tvOptions);
        tvOptions.setVisibility(View.VISIBLE);
        tvName.setText("Name : "+lessions.get(pos).getAnswer());
        TextView tvCTypeLevel = (TextView)view.findViewById(R.id.tvCTypeLevel);
        tvCTypeLevel.setText("Type : "+lessions.get(pos).getCtype()+" - Level : "+lessions.get(pos).getLevel());
        ImageView icon = (ImageView) view.findViewById(R.id.iv);
        Glide.with(context).load(lessions.get(pos).getQuestion()).into(icon);
        String str="Option - 1 : "+lessions.get(pos).getOption1()+"\n"+"Option - 2 : "+lessions.get(pos).getOption2()+"\n"+"Option - 3 : "+lessions.get(pos).getOption3()+"\n";
        tvOptions.setText(str);
        btnEdit=(Button)view.findViewById(R.id.btnEdit);
        btnDelete=(Button)view.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData(lessions.get(pos).getId(),pos);
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, EditModelTestsActivity.class);
                intent.putExtra("id",lessions.get(pos).getId());
                intent.putExtra("answer",lessions.get(pos).getAnswer());

                intent.putExtra("option1",lessions.get(pos).getOption1());
                intent.putExtra("option2",lessions.get(pos).getOption2());
                intent.putExtra("option3",lessions.get(pos).getOption3());

                context.startActivity(intent);
            }
        });

        return view;
    }
    ProgressDialog pd;
    private void deleteData(String id,int pos) {
        pd = new ProgressDialog(context);
        pd.setTitle("Please wait,Data is being loaded...");
        pd.show();
        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<ResponseData> call = apiService.deleteTests(id);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                if(response.body().status.equals("true")) {
                    Toast.makeText(context,response.body().message , Toast.LENGTH_SHORT).show();
                    lessions.remove(pos);
                    notifyDataSetChanged();
                }else{
                    Toast.makeText(context,response.body().message , Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pd.dismiss();
            }
        });
    }
}