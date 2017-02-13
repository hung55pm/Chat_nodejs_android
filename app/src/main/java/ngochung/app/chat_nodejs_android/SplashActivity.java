package ngochung.app.chat_nodejs_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

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
                Intent in =new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(in);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
