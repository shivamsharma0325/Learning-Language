package com.launguagelearning;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.launguagelearning.api.EndPointUrl;
import com.launguagelearning.api.RetrofitInstance;
import com.launguagelearning.model.Lessons;
import com.launguagelearning.model.ModelTest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionsActivity extends BaseActivity {
    ImageView tv;
    Button submitbutton, quitbutton;
    RadioGroup radio_g;
    RadioButton rb1,rb2,rb3;
    int flag1=0;
    public static int marks=0,correct=0,wrong=0;
    List<ModelTest> alModelTest;
    TextView tvQuestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        marks=0;correct=0;wrong=0;
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("ctype")+" ["+getIntent().getStringExtra("level")+"]");
        alModelTest = new ArrayList<>();
        final TextView score = (TextView)findViewById(R.id.textView4);
        tvQuestion= (TextView)findViewById(R.id.tvQuestion);
        tvQuestion.setText("Question-1");
        submitbutton=(Button)findViewById(R.id.button3);
        quitbutton=(Button)findViewById(R.id.buttonquit);
        tv=(ImageView) findViewById(R.id.tvque);
        radio_g=(RadioGroup)findViewById(R.id.answersgrp);
        rb1=(RadioButton)findViewById(R.id.radioButton);
        rb2=(RadioButton)findViewById(R.id.radioButton2);
        rb3=(RadioButton)findViewById(R.id.radioButton3);
        getQuestions();
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radio_g.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(getApplicationContext(), "Please select one choice", Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton uans = (RadioButton) findViewById(radio_g.getCheckedRadioButtonId());
                String ansText = uans.getText().toString();
                if(ansText.equals(alModelTest.get(flag1).getAnswer())) {
                    correct++;
                    Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT).show();
                }
                else {
                    wrong++;
                    Toast.makeText(getApplicationContext(), "Wrong", Toast.LENGTH_SHORT).show();
                }
                flag1++;
                if (score != null)
                    score.setText(""+correct);

                if(flag1<alModelTest.size())
                {
                    Glide.with(QuestionsActivity.this)
                            .load(alModelTest.get(flag1).getQuestion())
                            .into(tv);
                    rb1.setText(alModelTest.get(flag1).getOption1());
                    rb2.setText(alModelTest.get(flag1).getOption2());
                    rb3.setText(alModelTest.get(flag1).getOption3());
                }
                else
                {
                    marks=correct;
                    Intent intent = new Intent(getApplicationContext(),ResultActivity.class);
                    intent.putExtra("ctype",getIntent().getStringExtra("ctype"));
                    intent.putExtra("level",getIntent().getStringExtra("level"));
                    intent.putExtra("marks",""+marks);
                    intent.putExtra("correct",""+correct);
                    intent.putExtra("wrong",""+wrong);
                    startActivity(intent);
                    finish();
                }
                radio_g.clearCheck();
                tvQuestion.setText("Question-"+(flag1+1));
            }
        });

        quitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    ProgressDialog pd;
    private void getQuestions() {
        pd = new ProgressDialog(QuestionsActivity.this);
        pd.setTitle("Please wait,Data is being loaded...");
        pd.show();
        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<ModelTest>> call = apiService.getQuestions(getIntent().getStringExtra("ctype"),getIntent().getStringExtra("level"));
        call.enqueue(new Callback<List<ModelTest>>() {
            @Override
            public void onResponse(Call<List<ModelTest>> call, Response<List<ModelTest>> response) {
                pd.dismiss();
                if (response.code()==200) {
                    if (response.body() != null) {
                        if(response.body().size()>0) {
                            //List<Lessions> listRequestedWork =response.body();
                            alModelTest = response.body();
                            if(alModelTest!=null) {
                                if(alModelTest.size()>0) {
                                    Collections.shuffle(alModelTest);
                                    Glide.with(QuestionsActivity.this)
                                            .load(alModelTest.get(flag1).getQuestion())
                                            .into(tv);
                                    rb1.setText(alModelTest.get(flag1).getOption1());
                                    rb2.setText(alModelTest.get(flag1).getOption2());
                                    rb3.setText(alModelTest.get(flag1).getOption3());
                                }else{
                                    Toast.makeText(getApplicationContext(),"No data found",Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(),"No data found",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<ModelTest>> call, Throwable t) {
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