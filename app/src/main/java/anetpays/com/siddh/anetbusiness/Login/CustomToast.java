package anetpays.com.siddh.anetbusiness.Login;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import anetpays.com.siddh.anetbusiness.R;

public class CustomToast {
    public void showToast(Context context, View view, String error){

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.custom_toast, (ViewGroup)view.findViewById(R.id.toast_root));
        TextView textView = (TextView)layout.findViewById(R.id.toast_error);
        textView.setText(error);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
    public void showToastActivity(Context context, View view, String error){
        LayoutInflater  layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView textView = (TextView)view.findViewById(R.id.toast_error);
        textView.setText(error);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }
}
