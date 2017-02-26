package ngochung.app.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.github.nkzawa.emitter.Emitter;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ngochung.app.Adapters.InvitationAdapters;
import ngochung.app.Adapters.LitsfriendAdapters;
import ngochung.app.Applications.MyApplication;
import ngochung.app.Connect.APIConnection;
import ngochung.app.Connect.JSONObjectRequestListener;
import ngochung.app.Constants.Constants;
import ngochung.app.Models.Acounts;
import ngochung.app.Models.Room;
import ngochung.app.Untils.SharedConfig;
import ngochung.app.Untils.SocketioHandling;
import ngochung.app.chat_nodejs_android.MessageDetailActivity;
import ngochung.app.chat_nodejs_android.R;
import com.github.nkzawa.socketio.client.Socket;
/**
 * Created by NGOCHUNG on 2/18/2017.
 */

public class ListFriendFragment extends Fragment {
    LitsfriendAdapters adapters;
    ListView lv;
    ArrayList<Acounts> arr;
    Socket mSocket;
    String user_id;
    Context mContext;
    public ListFriendFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view=inflater.inflate(R.layout.fragment_list_friend, container, false);
        mContext=getActivity();
        mSocket= MyApplication.mSocket;
        user_id= new SharedConfig(mContext).getValueString(SharedConfig.USER_ID);
        lv=(ListView)view.findViewById(R.id.lv_list_friend);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SocketioHandling.socketchat1v1connect(user_id,arr.get(position).getUser_id(),mSocket);
                mSocket.on(user_id, onNewMessage);

            }
        });

        GetAllFrend();

        return view;
    }
    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            JSONObject data = (JSONObject) args[0];
            int code;
            String room_id;
            try {
                code = data.getInt(Constants.CODE);
                room_id=data.getString(Constants.ROOM_ID);
                if(code==200){
                    Intent in = new Intent(mContext, MessageDetailActivity.class);
                    in.putExtra(Constants.ROOM_ID,room_id);
                    mContext.startActivity(in);
                    mSocket.off(user_id);
                    Log.i("lan thu i ","abc");
                }


            } catch (JSONException e) {
                return;
            }

            //addMessage(username, message);
        }
    };

    public void GetAllFrend() {
        String access_token= new SharedConfig(mContext).getValueString(SharedConfig.ACCESS_TOKEN);
        try {
            APIConnection.getallfriend(mContext, access_token, new JSONObjectRequestListener() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        int code=response.getInt(Constants.CODE);
                        if(code==200){
                            JSONArray jsonArray= response.getJSONArray(Constants.RESULT);
                           arr= new ArrayList<Acounts>();
                            if(jsonArray.length()!=0){
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Acounts ac = new Gson().fromJson(jsonArray.getJSONObject(i).toString(), Acounts.class);
                                    arr.add(ac);
                                }

                                    adapters= new LitsfriendAdapters(mContext,arr);
                                    lv.setAdapter(adapters);


                            }
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
                    showToast(getResources().getString(R.string.err_voley)+"loiiiiii");
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void showToast(String msg){
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(mSocket!=null) {
            mSocket.off(user_id);
        }

    }
    @Override
    public void onResume() {
        Log.i("aaaa","iiiiiiii");
        GetAllFrend();
        super.onResume();
    }
}