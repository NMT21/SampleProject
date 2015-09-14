package com.example.ice.testingproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.ice.testingproject.api.APIConfig;
import com.example.ice.testingproject.utility.Config;
import com.pnikosis.materialishprogress.ProgressWheel;

/**
 * Created by ICE on 09/11/2015.
 */
public class LoginActivity extends AppCompatActivity  implements View.OnClickListener{

    TextView register, usernametitle, passwordtitle;
    Button login;
    EditText username, password;
    ProgressWheel progress;
    View progressbackground;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        context = getApplicationContext();

        login = (Button) findViewById(R.id.login_button);
        register = (TextView) findViewById(R.id.login_register);
        //forgotpassword = (TextView) findViewById(R.id.login_forgotpassword);

        username = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_password);

        usernametitle = (TextView) findViewById(R.id.login_email_label);
        passwordtitle = (TextView) findViewById(R.id.login_password_label);

        progressbackground = findViewById(R.id.login_progresswheel_background);
        progress = (ProgressWheel) findViewById(R.id.progress_wheel);


        login.setOnClickListener(this);
        register.setOnClickListener(this);
        //forgotpassword.setOnClickListener(this);

        setEnglishText();

        SharedPreferences sPref = getApplicationContext().getSharedPreferences(Config.APP_PREFERENCE, MODE_PRIVATE);
        if (sPref.getBoolean(Config.WAS_LOGIN, false)) {

            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        showDialogForChangingRoute();
        progressbackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    private void setEnglishText() {
        login.setText("Login");
        register.setText("Register");
        //forgotpassword.setText("Forgot Password");
        usernametitle.setText("Username");

        passwordtitle.setText("Passsword");
    }

    public void showDialogForChangingRoute()
    {
        final SharedPreferences sPref = getSharedPreferences(Config.APP_PREFERENCE, MODE_PRIVATE);
        MaterialDialog materialdialog = (new com.afollestad.materialdialogs.MaterialDialog.Builder(this)).title("Server route").customView(R.layout.custom_dialog_changeroute, false).positiveText("Add").positiveColorRes(R.color.pink_500).negativeText("Cancel").negativeColorRes(R.color.grey_500).autoDismiss(false).callback(new com.afollestad.materialdialogs.MaterialDialog.ButtonCallback() {


            public void onNegative(MaterialDialog materialdialog1)
            {
                materialdialog1.dismiss();
            }

            public void onPositive(MaterialDialog materialdialog1)
            {
                EditText edittext = (EditText)materialdialog1.findViewById(R.id.customdialog_changeroute);
                android.content.SharedPreferences.Editor editor = sPref.edit();
                editor.putString(Config.SERVER_ROUTE, edittext.getText().toString());
                editor.commit();
                materialdialog1.dismiss();
            }

        }).build();

        ((EditText)materialdialog.findViewById(R.id.customdialog_changeroute)).setText(sPref.getString(Config.SERVER_ROUTE, APIConfig.BASE_URL));
        materialdialog.show();
    }

    @Override
    public void onClick(View view) {

    }
}
