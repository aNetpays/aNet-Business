package in.anetpays.siddhant.anet_business.UI;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import in.anetpays.siddhant.anet_business.Constants.SharedPreferencesConstants;
import in.anetpays.siddhant.anet_business.MainActivity;
import in.anetpays.siddhant.anet_business.R;
import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;

/**
 * Created by siddh on 19-02-2018.
 */

public class AcceptPayment extends AppCompatActivity implements View.OnClickListener
{
    private TextView AmountView, infoView;
    private SurfaceView surfaceView;
    private QREader qrEader;
    private SharedPreferences preferences;
    private Button stateButton;
    private static final String cameraPermission = Manifest.permission.CAMERA;
    private CoordinatorLayout coordinatorLayout1;
    private Snackbar snackbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.accept_payment);
        setTitle(R.string.acceptTitle);

        initViews();
        setListeners();
        setUpQReader();

        Intent intent = getIntent();
        String amountReceived = intent.getExtras().getString("amountReceived","");
        AmountView.setText(amountReceived);

    }

    private void initViews(){


        //second layout views
        AmountView    = (TextView)findViewById(R.id.code_info);
        infoView      = (TextView)findViewById(R.id.info2);
        stateButton   = (Button)findViewById(R.id.btn_start_stop);
        surfaceView   = (SurfaceView)findViewById(R.id.camera_view);
        coordinatorLayout1 = (CoordinatorLayout)findViewById(R.id.acceptPayment);

        stateButton.setVisibility(View.VISIBLE);
    }

    private void setListeners(){

        //second layout listeners
        stateButton.setOnClickListener(this);

    }

    void setUpQReader()
    {
        qrEader = new QREader.Builder(this, surfaceView, new QRDataListener() {
            @Override
            public void onDetected(final String data) {
                Log.d("QR READER", "Value : " + data);
                infoView.post(new Runnable() {
                    @Override
                    public void run() {
                        infoView.setText(data);
                    }
                });
            }
        })
                .facing(QREader.BACK_CAM)
                .enableAutofocus(true)
                .height(surfaceView.getHeight())
                .width(surfaceView.getWidth())
                .build();
    }

    public void onClick(View v){
        switch (v.getId())
        {
            case R.id.btn_start_stop:
                super.onBackPressed();


        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        qrEader.releaseAndCleanup();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        qrEader.initAndStart(surfaceView);

    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(AcceptPayment.this, StartPayment.class));
    }

}
