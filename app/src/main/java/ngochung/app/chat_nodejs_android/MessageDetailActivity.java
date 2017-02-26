package ngochung.app.chat_nodejs_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ngochung.app.Adapters.MessagesListAdapters;
import ngochung.app.Applications.MyApplication;
import ngochung.app.Connect.APIConnection;
import ngochung.app.Connect.JSONObjectRequestListener;
import ngochung.app.Constants.Constants;
import ngochung.app.Models.MesRecent;
import ngochung.app.Models.Message;
import ngochung.app.Models.Room;
import ngochung.app.Untils.SharedConfig;
import ngochung.app.Untils.SocketioHandling;

/**
 * Created by NGOCHUNG on 2/22/2017.
 */

public class MessageDetailActivity extends AppCompatActivity {
    private Button btsend;
    private EditText contend;
    private ListView lv;
    private List<Message> listmessage;
    private MessagesListAdapters adapters;
    private Socket mSocket;
    private Room room;
    public static String room_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);
        mSocket= MyApplication.mSocket;
        lv=(ListView)findViewById(R.id.lv_chat_detail);
        btsend=(Button)findViewById(R.id.bt_send);
        contend=(EditText)findViewById(R.id.ed_content);
        Intent in = getIntent();
        room_id=in.getStringExtra(Constants.ROOM_ID);
        getallmessagebyroom_id(room_id);
        mSocket.on(room_id, onNewMessage);
        btsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg= contend.getText().toString();
                if(!msg.equals("")){
                    SocketioHandling.socketsend1vs1(MessageDetailActivity.this,room_id,msg,mSocket);
                    contend.setText("");
                }
            }
        });
        //Log.i("intent",room.getRoom_id()+"  "+room.getList().size());

    }
    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            JSONObject data = (JSONObject) args[0];

            try {
                int code=data.getInt(Constants.CODE);
                JSONObject jsonObject= data.getJSONObject(Constants.RESULT);
                Message message= new Gson().fromJson(jsonObject.toString(),Message.class);
                appendMessage(message);
                Log.i("logaaaa","   "+message.getMessage());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //addMessage(username, message);
        }
    };

    private void appendMessage(final Message m) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                listmessage.add(m);

                adapters.notifyDataSetChanged();

                // Playing device's notification
               // playBeep();
            }
        });
    }

    public void getallmessagebyroom_id(String room_id){
        String acc_token= new SharedConfig(MessageDetailActivity.this).getValueString(SharedConfig.ACCESS_TOKEN);
        try {
            APIConnection.getmessagedetail(MessageDetailActivity.this, room_id, acc_token, new JSONObjectRequestListener() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        listmessage= new ArrayList<Message>();
                        int code=response.getInt(Constants.CODE);
                        if(code==200){
                            JSONArray jsonArray= response.getJSONArray(Constants.RESULT);

                            for (int i=0;i<jsonArray.length();i++){
                                Message mes=new Gson().fromJson(jsonArray.getJSONObject(i).toString(),Message.class);
                                listmessage.add(mes);
                            }
                            adapters= new MessagesListAdapters(MessageDetailActivity.this,listmessage);
                            lv.setAdapter(adapters);
                        }else {
                            showToast(getResources().getString(R.string.retrieve_data_error));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();

                        showToast(getResources().getString(R.string.err));

                    }
                }

                @Override
                public void onError(VolleyError error) {
                    showToast(getResources().getString(R.string.err_voley));
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void showToast(String msg){
        Toast.makeText(MessageDetailActivity.this,msg,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
