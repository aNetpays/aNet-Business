package in.anetpays.siddhant.anet_business.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.davidmiguel.numberkeyboard.NumberKeyboard;
import com.davidmiguel.numberkeyboard.NumberKeyboardListener;

import in.anetpays.siddhant.anet_business.R;

public class StartPayment extends AppCompatActivity implements NumberKeyboardListener, View.OnClickListener{

    private TextView amountTextView;
    private Button startPayment;
    private NumberKeyboard numberKeyboard;
    private String amountText;
    private double amountDouble;
    private String amountToBeSent;
    private CoordinatorLayout coordinatorLayout;

    private static final double MAX_ALLOWED_AMOUNT = 9999.99;
    private static final int MAX_ALLOWED_DECIMALS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_activity);
        setTitle(R.string.activityTitle);

        initViews();
        setListeners();

    }
    public StartPayment(){
        this.amountText = "";
        this.amountDouble = 0.0;
    }

    private void initViews(){

        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coOrdinatorStartPayment);
        amountTextView = (TextView) findViewById(R.id.amount);
        numberKeyboard = (NumberKeyboard) findViewById(R.id.numberKeyboard);
        startPayment = (Button)findViewById(R.id.startPayment);

    }
    private void setListeners(){

        startPayment.setOnClickListener(this);

        numberKeyboard.setListener(this);
    }


    public void onClick(View v){
        switch (v.getId())
        {
            case R.id.startPayment:
                Intent intent = new Intent(this, AcceptPayment.class);
                intent.putExtra("amountReceived", amountToBeSent);
                startActivity(intent);
                finish();

        }
    }
    @Override
    public void onLeftAuxButtonClicked(){
        if (!hasComma(amountText)) {
            amountText = amountText.isEmpty() ? "0." : amountText + ".";
            showAmount(amountText);
        }
    }

    @Override
    public void onRightAuxButtonClicked(){
        if (amountText.isEmpty()) {
            return;
        }
        String newAmountText;
        if (amountText.length() <= 1) {
            newAmountText = "";
        } else {
            newAmountText = amountText.substring(0, amountText.length() - 1);
            if (newAmountText.charAt(newAmountText.length() - 1) == '.') {
                newAmountText = newAmountText.substring(0, newAmountText.length() - 1);
            }
            if ("0".equals(newAmountText)) {
                newAmountText = "";
            }
        }
        updateAmount(newAmountText);

    }

    @Override
    public void onNumberClicked(int number) {
        if (amountText.isEmpty() && number == 0) {
            return;
        }
        updateAmount(amountText + number);

    }

    private void updateAmount(String newAmountText){
        double newAmount = newAmountText.isEmpty() ? 0.0 : Double.parseDouble(newAmountText.replaceAll(",", "."));
        if (newAmount >= 0.0 && newAmount <= MAX_ALLOWED_AMOUNT
                && getNumDecimals(newAmountText) <= MAX_ALLOWED_DECIMALS) {
            amountText = newAmountText;
            amountDouble = newAmount;
            showAmount(amountText);
        }
        else
        {
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Please Enter Amount under $9999.99 only", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    private void showAmount(String amountRecieved) {
        String moneySymbol = "";
        moneySymbol = getResources().getString(R.string.moneySymbol);
        amountTextView.setText(moneySymbol + (amountRecieved.isEmpty() ? "0" : addThousandSeparator(amountRecieved)));
        amountToBeSent = amountTextView.getText().toString();
    }

    private String addThousandSeparator(String amount) {
        String integer;
        String decimal;
        if (amount.indexOf('.') >= 0) {
            integer = amount.substring(0, amount.indexOf('.'));
            decimal = amount.substring(amount.indexOf('.'), amount.length());
        } else {
            integer = amount;
            decimal = "";
        }
        if (integer.length() > 3) {
            StringBuilder tmp = new StringBuilder(integer);
            for (int i = integer.length() - 3; i > 0; i = i - 3) {
                tmp.insert(i, ",");
            }
            integer = tmp.toString();
        }
        return integer + decimal;
    }

    private int getNumDecimals(String num) {
        if (!hasComma(num)) {
            return 0;
        }
        return num.substring(num.indexOf('.') + 1, num.length()).length();
    }

    private boolean hasComma(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '.') {
                return true;
            }
        }
        return false;
    }
}
