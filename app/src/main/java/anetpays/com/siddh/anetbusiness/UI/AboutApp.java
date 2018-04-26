package anetpays.com.siddh.anetbusiness.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import anetpays.com.siddh.anetbusiness.R;


/**
 * Created by siddh on 19-02-2018.
 */

public class AboutApp extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_activity, container, false);
        getActivity().setTitle("About");

        return view;
    }
}
