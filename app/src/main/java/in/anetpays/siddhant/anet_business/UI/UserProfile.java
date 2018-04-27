package in.anetpays.siddhant.anet_business.UI;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.omadahealth.lollipin.lib.managers.AppLock;

import in.anetpays.siddhant.anet_business.Constants.SharedPreferencesConstants;
import in.anetpays.siddhant.anet_business.Extras.CustomPinActivity;
import in.anetpays.siddhant.anet_business.R;

import static in.anetpays.siddhant.anet_business.Constants.SharedPreferencesConstants.MOBILENUMBER;


/**
 * Created by siddh on 19-02-2018.
 */

public class UserProfile extends Fragment implements View.OnClickListener {

    TextView textView, firstName, fatherName, motherName,emailAddress,mobileNumber, address;
    Button pinButton;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_activity, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initViews();
        setListeners();
        setEditText();

        return view;

    }

    private void initViews(){
        firstName    = (TextView) view.findViewById(R.id.firstNameEdit);
        fatherName   = (TextView) view.findViewById(R.id.fatherNameEdit);
        motherName   = (TextView) view.findViewById(R.id.motherNameEdit);
        mobileNumber = (TextView) view.findViewById(R.id.mobileNumber);
        emailAddress = (TextView) view.findViewById(R.id.emailaddressEdit);
        address      = (TextView) view.findViewById(R.id.addressEdit);
        textView     = (TextView)view.findViewById(R.id.changeInfoText);
        pinButton    = (Button)view.findViewById(R.id.changePin);

        textView.setText(Html.fromHtml(getString(R.string.changeInfoTextString)));
    }
    private void setEditText(){
        firstName.setText(SharedPreferencesConstants.NAME);
        fatherName.setText(SharedPreferencesConstants.FATHERNAME);
        motherName.setText(SharedPreferencesConstants.MOTHERNAME);
        mobileNumber.setText(MOBILENUMBER);
        address.setText(SharedPreferencesConstants.ADDRESS);
        emailAddress.setText(SharedPreferencesConstants.EMAIL);
    }

    private void setListeners(){
        pinButton.setOnClickListener(this);
        mobileNumber.setOnClickListener(this);
        address.setOnClickListener(this);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.changePin:
                changePIN();
                break;

            case R.id.mobileNumber:
                changePhoneInfo();
                break;

            case R.id.addressEdit:
                changeAddressInfo();
                break;

        }
    }

    private void changePIN(){

        Intent intent = new Intent(getContext(), CustomPinActivity.class);
        intent.putExtra(AppLock.EXTRA_TYPE, AppLock.CHANGE_PIN);
        startActivity(intent);
    }

    private void changeAddressInfo(){
        final String tempAddress = SharedPreferencesConstants.ADDRESS;
        new MaterialDialog.Builder(getActivity())
                .title(R.string.dialogTitleAddress)
                .content(R.string.addressBody)
                .inputType(
                        InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .positiveText(R.string.submitDialog)
                .alwaysCallInputCallback()
                .input(R.string.addressHint, 0, false,
                        (dialog, input) -> {
                            if (input.toString().equalsIgnoreCase(tempAddress)) {
                                dialog.setContent(R.string.addressError);
                                dialog.getActionButton(DialogAction.POSITIVE).setEnabled(false);
                            } else {
                                dialog.setContent(R.string.addressBody);
                                dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
                            }
                        })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Toast.makeText(getActivity(), "Updated Data Successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    private void changePhoneInfo(){

        final String tempMobile = SharedPreferencesConstants.MOBILENUMBER;
        new MaterialDialog.Builder(getActivity())
                .title(R.string.dialogTitlePhoneNumber)
                .content(R.string.phoneBody)
                .inputType(
                        InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .positiveText(R.string.submitDialog)
                .alwaysCallInputCallback()
                .input(R.string.phoneHint, 0, false,
                        (dialog, input) -> {
                            if (input.toString().equalsIgnoreCase(tempMobile)) {
                                dialog.setContent(R.string.phoneError);
                                dialog.getActionButton(DialogAction.POSITIVE).setEnabled(false);
                            } else {
                                dialog.setContent(R.string.phoneBody);
                                dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
                            }
                        })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Toast.makeText(getActivity(), "Submitted Data Successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();

    }
    private void blockEdit(EditText editText){
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }
}
