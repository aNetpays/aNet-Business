package anetpays.com.siddh.anetbusiness.SplashScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;


import anetpays.com.siddh.anetbusiness.Login.MainLoginActivity;
import anetpays.com.siddh.anetbusiness.R;

public class SplashScreen extends Activity implements DownLoadingActivity.LoadingTaskFinishedListener{

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.activity_splash_progress_bar);
        new DownLoadingActivity(progressBar, this).execute("www.google.co.in");
    }


    @Override
    public void onTaskFinished(){
        completeSplash();
    }

    private void completeSplash(){
        startApp();
        finish();
    }
    private void startApp(){
        Intent intent = new Intent(SplashScreen.this, MainLoginActivity.class);
        startActivity(intent);
    }
}
