package in.anetpays.siddhant.anet_business.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.anetpays.siddhant.anet_business.R;


/**
 * Created by siddh on 19-02-2018.
 */

public class AboutApp extends Fragment implements View.OnClickListener
{
    private View view;
    private TextView termsAndConditions, privacyNotice, FrequentQuestions, Version;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_about_activity, container, false);
        getActivity().setTitle("About");
        initViews();
        setListeners();


        return view;
    }

    private void initViews(){
        termsAndConditions = (TextView)view.findViewById(R.id.termsConditionsTextView);
        privacyNotice = (TextView)view.findViewById(R.id.privacyNoticeTextView);
        FrequentQuestions = (TextView)view.findViewById(R.id.FAQTextView);
        Version =(TextView)view.findViewById(R.id.versionTextView);

    }
    private void setListeners() {
        termsAndConditions.setOnClickListener(this);
        privacyNotice.setOnClickListener(this);
        FrequentQuestions.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.termsConditionsTextView:
                Intent intent = new Intent(getActivity(), TermsAndConditions.class);
                intent.putExtra("url", "urlterms");
                startActivity(intent);
                break;
            case R.id.privacyNoticeTextView:
                Intent intent1 = new Intent(getActivity(), TermsAndConditions.class);
                intent1.putExtra("url", "urlPrivacy");
                startActivity(intent1);

        }

    }
}
