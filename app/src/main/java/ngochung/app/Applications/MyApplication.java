package ngochung.app.Applications;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;


import java.util.List;

import ngochung.app.Untils.SharedConfig;

/**
 * Created by vnGame on 12/29/16.
 */

public class MyApplication extends Application {

    private static MyApplication mInstance;
    public static boolean login;
    public static String access_token;

    public static MyApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        getStatusLogin(this);
    }

    public void getStatusLogin(Context context) {
//        SharedPreferences shared = new SharedConfig(context).GetConfig();
//        login = shared.getBoolean(SharedConfig.LOGIN, false);
//        access_token = shared.getString(SharedConfig.ACCESS_TOKEN,"");
    }

}
