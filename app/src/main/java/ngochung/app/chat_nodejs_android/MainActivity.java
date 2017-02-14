package ngochung.app.chat_nodejs_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

import ngochung.app.Applications.MyApplication;
import ngochung.app.Constants.Constants;
import ngochung.app.Untils.SharedConfig;

public class MainActivity extends AppCompatActivity {
    public static String MAIN_LOG="MainActivity";
    private Socket mSocket;
    {
     try {
            mSocket= IO.socket(Constants.URL_SOCKET);
     }catch (URISyntaxException e){

     }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showToast(new SharedConfig(getBaseContext()).getValueBoolean(SharedConfig.LOGIN)+"    "+new SharedConfig(getBaseContext()).getValueString(SharedConfig.ACCESS_TOKEN));
        mSocket.connect();
    }

    public void showToast(String msg){
        Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();

    }
}
