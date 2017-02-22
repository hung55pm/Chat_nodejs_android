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

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ngochung.app.Adapters.MessagesListAdapters;
import ngochung.app.Applications.MyApplication;
import ngochung.app.Constants.Constants;
import ngochung.app.Models.Message;
import ngochung.app.Models.Room;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);
        mSocket= MyApplication.mSocket;
        lv=(ListView)findViewById(R.id.lv_chat_detail);
        btsend=(Button)findViewById(R.id.bt_send);
        contend=(EditText)findViewById(R.id.ed_content);
        Intent in = getIntent();
        String value=in.getStringExtra(Constants.ROOM_ID);
        room= new Gson().fromJson(value,Room.class);
        listmessage=room.getList();
        mSocket.on(room.getRoom_id(), onNewMessage);
        adapters= new MessagesListAdapters(MessageDetailActivity.this,listmessage);
        lv.setAdapter(adapters);
        btsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg= contend.getText().toString();
                if(!msg.equals("")){
                    SocketioHandling.socketsend1vs1(MessageDetailActivity.this,room.getRoom_id(),msg,mSocket);
                    contend.setText("");
                }
            }
        });
        Log.i("intent",room.getRoom_id()+"  "+room.getList().size());

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
}
