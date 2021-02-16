package com.launguagelearning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.speech.tts.TextToSpeech;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.launguagelearning.api.EndPointUrl;
import com.launguagelearning.api.RetrofitInstance;
import com.launguagelearning.model.Lessons;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimalsLearningActivity extends BaseActivity implements View.OnClickListener {

    ViewPager mViewPager;
    Button button_previous,button_next;
    ImageView iv_playbutton;

    AnimalViewPagerAdapter mViewPagerAdapter;
    private TextToSpeech t1;
    int pos = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Animals");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        button_previous=(Button)findViewById(R.id.button_previous);
        button_next=(Button)findViewById(R.id.button_next);
        button_next.setOnClickListener(this);
        button_previous.setOnClickListener(this);



        iv_playbutton=(ImageView)findViewById(R.id.iv_playbutton);
        iv_playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lessions!=null) {
                    t1.speak(lessions.get(pos).getCname(), TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });

        mViewPager = (ViewPager)findViewById(R.id.viewPagerMain);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pos = position;
                t1.speak(lessions.get(pos).getCname(), TextToSpeech.QUEUE_FLUSH, null);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        getLessions();
    }

List<Lessons> lessions;
    ProgressDialog pd;
    private void getLessions() {
        pd = new ProgressDialog(AnimalsLearningActivity.this);
        pd.setTitle("Please wait,Data is being loaded...");
        pd.show();
        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<Lessons>> call = apiService.getAnimalsLessions();
        call.enqueue(new Callback<List<Lessons>>() {
            @Override
            public void onResponse(Call<List<Lessons>> call, Response<List<Lessons>> response) {
                pd.dismiss();
                if (response.code()==200) {
                    if (response.body() != null) {
                        if(response.body().size()>0) {
                            //List<Lessions> listRequestedWork =response.body();
                            lessions = response.body();
                            mViewPagerAdapter = new AnimalViewPagerAdapter(AnimalsLearningActivity.this, response.body());
                            mViewPager.setAdapter(mViewPagerAdapter);
                            t1.speak(response.body().get(0).getCname(), TextToSpeech.QUEUE_FLUSH, null);
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Lessons>> call, Throwable t) {
                pd.dismiss();
            }
        });
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.button_previous:
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
                break;

            case R.id.button_next:
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
                break;
        }
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