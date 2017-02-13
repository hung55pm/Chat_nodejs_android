package ngochung.app.chat_nodejs_android;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import ngochung.app.Connect.APIConnection;
import ngochung.app.Connect.JSONObjectRequestListener;

public class LoginActivity extends AppCompatActivity {

    private EditText phone, pass;
    private Button btlogin;
    private TextView txtregister, txtfogotpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_phone=phone.getText().toString();
                String str_pass= pass.getText().toString();
                login(str_phone,str_pass);
            }
        });

        txtregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        txtfogotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    public void init(){
        phone=(EditText)findViewById(R.id.txt_phone);
        pass=(EditText)findViewById(R.id.txt_password);
        btlogin=(Button)findViewById(R.id.bt_login);
        txtregister=(TextView)findViewById(R.id.txt_resgiter);
        txtfogotpassword=(TextView)findViewById(R.id.txt_fogotpassword);

    }

    public void login(String phone, String pass){
        try {
            APIConnection.login(LoginActivity.this, phone, pass, new JSONObjectRequestListener() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        int code= response.getInt("code");
                        if(code==200){
                            JSONObject jsonObject= response.getJSONObject("result");
                            IntenStart(MainActivity.class);

                        }else {
                            showToast(getResources().getString(R.string.err));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(VolleyError error) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void showToast(String msg){
        Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_SHORT).show();

    }
    public void IntenStart(Class cls){
        Intent in = new Intent(LoginActivity.this, cls);
        startActivity(in);
    }

}
