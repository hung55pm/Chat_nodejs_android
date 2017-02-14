package ngochung.app.chat_nodejs_android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import ngochung.app.Applications.MyApplication;
import ngochung.app.Constants.Constants;
import ngochung.app.Untils.SharedConfig;

/**
 * Created by MR_HUNG on 2/12/2017.
 */

public class SplashActivity extends Activity {
    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isNetworkConnected()) {
                    confirmInternetFailDialog();
                }else {
                    directLogin();
                }
            }
        }, SPLASH_TIME_OUT);
    }

    public void directLogin(){
        Boolean login= new SharedConfig(getBaseContext()).getValueBoolean(SharedConfig.LOGIN);
        if(login){
            IntenStart(MainActivity.class);
            finish();
        }else {
            IntenStart(LoginActivity.class);
            finish();
        }
    }

    public void IntenStart(Class cls){
        Intent in = new Intent(SplashActivity.this, cls);
        startActivity(in);
    }

    private void confirmInternetFailDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.not_connect_internet))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.try_again), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (!isNetworkConnected()) {
                            confirmInternetFailDialog();
                        } else {
                            directLogin();
                        }
                    }
                })
                .setNegativeButton(getResources().getString(R.string.exit), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public void showToast(String msg){
        Toast.makeText(SplashActivity.this,msg,Toast.LENGTH_SHORT).show();

    }
}
