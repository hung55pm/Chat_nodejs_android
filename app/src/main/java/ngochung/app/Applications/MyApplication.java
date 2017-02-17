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
    public static String PHONE_KEY_SEARCH_FRIEND="";
    public static Boolean CHECK_SEARCH=false;

    public static MyApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

    }

}
