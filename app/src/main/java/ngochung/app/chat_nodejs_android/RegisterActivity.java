package ngochung.app.chat_nodejs_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import ngochung.app.Connect.APIConnection;
import ngochung.app.Connect.JSONObjectRequestListener;
import ngochung.app.Constants.Constants;
import ngochung.app.Models.Acounts;

public class RegisterActivity extends AppCompatActivity {
    public static String RES_LOG="RegisterActivity";
    private EditText ed_phone,ed_name,ed_pass,ed_rp_pass,ed_email;
    private Button bt_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone=ed_phone.getText().toString();
                String name=ed_name.getText().toString();
                String pass=ed_pass.getText().toString();
                String email=ed_email.getText().toString();

                if(phone.equals("")||name.equals("")||pass.equals("") || email.equals("")){
                    showToast(getResources().getString(R.string.parameter_missing));
                }else {
                    if(pass.equals(ed_rp_pass.getText().toString())){
                        Acounts acounts= new Acounts(phone,name,email,pass);
                        Register(acounts);

                    }else {
                        showToast(getResources().getString(R.string.password_not_same));
                    }
                }
            }
        });

    }

    public  void init(){
        ed_phone=(EditText)findViewById(R.id.ed_re_phone);
        ed_name=(EditText)findViewById(R.id.ed_re_name);
        ed_pass=(EditText)findViewById(R.id.ed_re_pass);
        ed_rp_pass=(EditText)findViewById(R.id.ed_re_rp_pass);
        ed_email=(EditText)findViewById(R.id.ed_re_email);
        bt_register=(Button) findViewById(R.id.bt_register);
    }

    public void Register (Acounts acounts){

        try {
            APIConnection.register(RegisterActivity.this, acounts, new JSONObjectRequestListener() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        int code= response.getInt(Constants.CODE);
                        Log.i(RES_LOG,""+code);
                        if(code==200){
                            finish();
                            showToast(getResources().getString(R.string.register_success));
                        }else {
                            showToast(getResources().getString(R.string.register_fail));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        showToast(getResources().getString(R.string.err));
                    }

                }

                @Override
                public void onError(VolleyError error) {
                    showToast(getResources().getString(R.string.retrieve_data_error));
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void showToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
    public void IntenStart(Class cls){
        Intent in = new Intent(RegisterActivity.this, cls);
        startActivity(in);
    }
}
