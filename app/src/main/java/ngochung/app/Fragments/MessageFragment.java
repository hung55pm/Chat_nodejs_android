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
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ngochung.app.Adapters.ListMesRecentAdapters;
import ngochung.app.Connect.APIConnection;
import ngochung.app.Connect.JSONObjectRequestListener;
import ngochung.app.Constants.Constants;
import ngochung.app.Models.Acounts;
import ngochung.app.Models.MesRecent;
import ngochung.app.Untils.SharedConfig;
import ngochung.app.chat_nodejs_android.MessageDetailActivity;
import ngochung.app.chat_nodejs_android.R;

/**
 * Created by NGOCHUNG on 2/17/2017.
 */

public class MessageFragment extends Fragment {
    Context mContext;
    ListView lv;
    ListMesRecentAdapters adapters;
    ArrayList<MesRecent> data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        mContext= getActivity();
        lv=(ListView)view.findViewById(R.id.lv_mes_recent);
        getMessageRecent();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(mContext, MessageDetailActivity.class);
                i.putExtra(Constants.ROOM_ID,data.get(position).getRoom_id());
                startActivity(i);
            }
        });
        return view;
    }

    public void getMessageRecent(){
        try {
            APIConnection.getMessRecent(mContext, new JSONObjectRequestListener() {
                @Override
                public void onSuccess(JSONObject response) {
                    Log.i("abc",response.toString());
                    try {
                       data= new ArrayList<MesRecent>();
                        int code =response.getInt(Constants.CODE);
                        if(code==200){
                            JSONArray jsonArray= response.getJSONArray(Constants.RESULT);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                MesRecent ac = new Gson().fromJson(jsonArray.getJSONObject(i).toString(), MesRecent.class);
                                data.add(ac);
                            }
                            adapters= new ListMesRecentAdapters(mContext,data);
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
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onResume() {
        getMessageRecent();
        super.onResume();
    }
}
