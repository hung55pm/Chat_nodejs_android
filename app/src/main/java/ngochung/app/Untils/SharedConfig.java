package ngochung.app.Untils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by vnGame on 12/30/16.
 */

public class SharedConfig {

    Context context;
    SharedPreferences shared;
    public static final String LOGIN = "LOGIN";
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";

    public SharedConfig(Context context) {
        this.context = context;
        shared = context.getSharedPreferences("CONFIG", Context.MODE_PRIVATE);
    }

    public SharedPreferences GetConfig() {
        return shared;
    }

    public void setValueBoolean(String key, boolean value) {
        SharedPreferences.Editor edit = shared.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public void setValueString(String key, String value) {
        SharedPreferences.Editor edit = shared.edit();
        edit.putString(key, value);
        edit.commit();
    }
    public Boolean getValueBoolean(String key){
       return shared.getBoolean(key,false);

    }
    public String getValueString(String key){
        return  shared.getString(key,"");
    }

}
