package in.anetpays.siddhant.anet_business.UI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import in.anetpays.siddhant.anet_business.Constants.SharedPreferencesConstants;
import in.anetpays.siddhant.anet_business.R;
import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;


/**
 * Created by siddh on 19-02-2018.
 */

public class AcceptPayment extends Fragment implements View.OnClickListener
{
    private TextView textView;
    private SurfaceView surfaceView;
    private QREader qrEader;
    private SharedPreferences preferences;
    private Button stateButton, restartButton;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_payment_activity, container, false);
        getActivity().setTitle("Payment");
        initViews();
        setListeners();

        setUpQReader();

        return view;
    }

    private void initViews(){



        //second layout views
        textView      = (TextView)view.findViewById(R.id.code_info);
        stateButton   = (Button)view.findViewById(R.id.btn_start_stop);
        restartButton = (Button)view.findViewById(R.id.btn_restart_activity);
        surfaceView   = (SurfaceView)view.findViewById(R.id.camera_view);

        stateButton.setVisibility(View.VISIBLE);
    }

    private void setListeners(){

        //second layout listeners
        stateButton.setOnClickListener(this);
        restartButton.setOnClickListener(this);
    }
    public void restartActivity()
    {
        Fragment CurrentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (CurrentFragment instanceof AcceptPayment)
        {
            FragmentTransaction fragmentTransaction = (getActivity()).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.detach(CurrentFragment);
            fragmentTransaction.attach(CurrentFragment);
            fragmentTransaction.commit();
        }
    }
    void setUpQReader()
    {
        qrEader = new QREader.Builder(getActivity(), surfaceView, new QRDataListener() {
            @Override
            public void onDetected(final String data) {
                Log.d("QR READER", "Value : " + data);
                textView.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(data);
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
                if (preferences.getBoolean(SharedPreferencesConstants.hasCameraPer, false))

                {
                    if (qrEader.isCameraRunning())
                    {
                        stateButton.setText("Start Payment");
                        qrEader.stop();
                    }
                    else
                    {
                        stateButton.setText("Stop Scanning");
                        qrEader.start();
                    }
                }
                break;

            case R.id.btn_restart_activity:
                restartActivity();
                break;

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
}
