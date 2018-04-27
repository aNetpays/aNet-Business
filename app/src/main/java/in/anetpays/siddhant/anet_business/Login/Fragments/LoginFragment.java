package in.anetpays.siddhant.anet_business.Login.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import in.anetpays.siddhant.anet_business.Constants.SharedPreferencesConstants;
import in.anetpays.siddhant.anet_business.Login.CustomToast;
import in.anetpays.siddhant.anet_business.Login.Utils;
import in.anetpays.siddhant.anet_business.MainActivity;
import in.anetpays.siddhant.anet_business.R;

import static in.anetpays.siddhant.anet_business.Constants.SharedPreferencesConstants.PREF_LOGIN;

public class LoginFragment extends Fragment implements View.OnClickListener {

    protected View view;
    private static FragmentManager fragmentManager;
    private static ProgressDialog progressDialog;
    private static SharedPreferences sharedPreferences;
    private EditText emailID, password;
    private Button loginButton;
    private TextView forgotPassword, signUp;
    private CheckBox show_hide_password;
    private RelativeLayout loginLayout;
    private Animation shakeAnimation;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState){
        view = layoutInflater.inflate(R.layout.login_layout, null, false);

        initViews();
        setListeners();
        //performChecks();
        return view;
    }


    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.loginBtn:
                                checkalidation();
                                break;
            case R.id.forgot_password:
                                        fragmentManager
                                                .beginTransaction()
                                                .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                                                .replace(R.id.frame, new ForgotPasswordFragment(), Utils.Forgot_Password).commit();
                                        break;
            case R.id.createAccount:
                                        fragmentManager
                                                .beginTransaction()
                                                .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                                                .replace(R.id.frame, new SignUpFragment(), Utils.SignUp_Fragment).commit();
                                        break;
        }

    }

    private void initViews(){
        fragmentManager = this.getFragmentManager();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        emailID = (EditText)view.findViewById(R.id.login_emailid);
        password = (EditText)view.findViewById(R.id.login_password);
        loginButton = (Button)view.findViewById(R.id.loginBtn);
        forgotPassword = (TextView)view.findViewById(R.id.forgot_password);
        signUp = (TextView)view.findViewById(R.id.createAccount);
        show_hide_password = (CheckBox)view.findViewById(R.id.show_hide_password);
        loginLayout = (RelativeLayout)view.findViewById(R.id.login_layout);
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);

        XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try
        {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),xrp);
            forgotPassword.setTextColor(csl);
            show_hide_password.setTextColor(csl);
            show_hide_password.setTextColor(csl);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void setListeners(){
        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        signUp.setOnClickListener(this);
        show_hide_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    show_hide_password.setText(R.string.hide_pwd);
                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    show_hide_password.setText(R.string.show_pwd);
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }
    private void checkalidation(){

        String CemailID = emailID.getText().toString();
        String Cpaassword = password.getText().toString();

        Pattern pattern = Pattern.compile(Utils.regEx);
        Matcher matcher = pattern.matcher(CemailID);

        if (CemailID.equals("") || CemailID.length() == 0 || Cpaassword.equals("") || Cpaassword.length() == 0)
        {
            loginLayout.startAnimation(shakeAnimation);
            new CustomToast().showToast(getActivity(), view, "Enter both Credentials");
        }
        else if (!matcher.find())
        {
            loginLayout.startAnimation(shakeAnimation);
            new CustomToast().showToast(getActivity(), view, "EMAIL ID is Invalid");
        }
        else
        {
            progressDialog.setMessage("Logging you in...");
            showDialog();
            LoginProcess(CemailID, Cpaassword);
        }
    }

    private void LoginProcess (String email, String pwd){
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(PREF_LOGIN, Context.MODE_PRIVATE).edit();
        editor.putBoolean(SharedPreferencesConstants.IS_LOGGED_IN, true);
        editor.apply();
        hideDialog();
        goToProfile();


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

    private void goToProfile()
    {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
