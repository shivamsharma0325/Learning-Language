package com.launguagelearning;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends Activity {
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.acitivity_splash);
        final int ScreenDisplay = 1000;
        Thread t1=new Thread(){
            int wait1=0;
            public void run(){
                try{
                    while(wait1<=ScreenDisplay )
                    {
                        sleep(100);
                        wait1+=100;
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                finally{
                    sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                    String fg = sharedpreferences.getString("is_first","yes");
        if(fg.equals("no")){
            startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
            finish();
        }else {
            Intent intentg = new Intent(SplashActivity.this, LoginActivity.class);
            intentg.putExtra("from", "dash");
            startActivity(intentg);
            finish();
        }
                }
            }
        };
        t1.start();
    }
}
