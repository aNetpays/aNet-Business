package anetpays.com.siddh.anetbusiness.Login.Fragments;

import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import anetpays.com.siddh.anetbusiness.Login.CustomToast;
import anetpays.com.siddh.anetbusiness.Login.MainLoginActivity;
import anetpays.com.siddh.anetbusiness.Login.Utils;
import anetpays.com.siddh.anetbusiness.R;

public class SignUpFragment extends Fragment implements View.OnClickListener {

    protected View view;
    private EditText fullName, emailID, mobileNumber, location, password, confirmPassword;
    private TextView login;
    private Button signUpButton;
    private CheckBox terms_conditions;
    private ProgressDialog progressDialog;
    private LinearLayout linearLayout;
    private Animation shakeAnimation;


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){

        view = layoutInflater.inflate(R.layout.signup_layout, container, false);
        initViews();
        setListeners();
         return view;
     }

     @Override
    public void onClick(View v){

        switch (v.getId())
        {
            case R.id.signUpBtn:
                                checkValidation();
                                break;
            case R.id.already_user:
                                new MainLoginActivity().replaceFragment();
                                break;
        }

     }

     private void initViews(){

        fullName = (EditText)view.findViewById(R.id.fullName);
        emailID = (EditText)view.findViewById(R.id.userEmailId);
        mobileNumber = (EditText)view.findViewById(R.id.mobileNumber);
        location = (EditText)view.findViewById(R.id.location);
        password = (EditText)view.findViewById(R.id.password);
        confirmPassword = (EditText)view.findViewById(R.id.confirmPassword);
        signUpButton = (Button)view.findViewById(R.id.signUpBtn);
        login = (TextView)view.findViewById(R.id.already_user);
        terms_conditions = (CheckBox)view.findViewById(R.id.terms_conditions);
        linearLayout = (LinearLayout)view.findViewById(R.id.signup_layout);
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
         XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
         try {
             ColorStateList csl = ColorStateList.createFromXml(getResources(),xrp);
             login.setTextColor(csl);
             terms_conditions.setTextColor(csl);
         } catch (Exception e) {
             e.printStackTrace();
         }


     }
     private void setListeners(){
        signUpButton.setOnClickListener(this);
        login.setOnClickListener(this);

     }

     private void checkValidation(){
        String getFullName = fullName.getText().toString();
        String getEmailID = emailID.getText().toString();
        String getMobileNumber = mobileNumber.getText().toString();
        String getLocation = location.getText().toString();
        String getPassword = password.getText().toString();
        String getConfirmPassword = confirmPassword.getText().toString();

         Pattern pattern = Pattern.compile(Utils.regEx);
         Matcher matcher = pattern.matcher(getEmailID);

         if (getFullName.equals("") || getFullName.length() == 0
                 || getEmailID.equals("") || getEmailID.length() == 0
                 || getMobileNumber.equals("") || getMobileNumber.length() == 0
                 || getLocation.equals("") || getLocation.length() == 0
                 || getPassword.equals("") || getPassword.length() == 0
                 || getConfirmPassword.equals("")
                 || getConfirmPassword.length() == 0) {
             linearLayout.startAnimation(shakeAnimation);

             new CustomToast().showToast(getActivity(), view,
                     "All fields are required.");
         }

             // Check if email id valid or not
         else if (!matcher.find())
         {
             new CustomToast().showToast(getActivity(), view,"Your Email Id is Invalid.");
         }
         else if (!getConfirmPassword.equals(getPassword)) {

             new CustomToast().showToast(getActivity(), view,"Both password doesn't match.");
         }
         else if (!terms_conditions.isChecked())
         {
             new CustomToast().showToast(getActivity(), view,"Please select Terms and Conditions.");
         }
         else

         {
             progressDialog.setMessage("Registering ......");
             showDialog();
             registerProcess(getFullName, getEmailID, getPassword);
         }
     }

     private void registerProcess(String name, String email, String password){
        hideDialog();
        new MainLoginActivity().replaceFragment();
     }

     private void showDialog(){
        if (!progressDialog.isShowing())
        {
            progressDialog.show();
        }
     }

     private void hideDialog(){
        if (progressDialog.isShowing())
        {
            progressDialog.hide();
        }
     }
}
