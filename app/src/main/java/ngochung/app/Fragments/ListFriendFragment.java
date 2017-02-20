package ngochung.app.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ngochung.app.Adapters.InvitationAdapters;
import ngochung.app.Adapters.LitsfriendAdapters;
import ngochung.app.Connect.APIConnection;
import ngochung.app.Connect.JSONObjectRequestListener;
import ngochung.app.Constants.Constants;
import ngochung.app.Models.Acounts;
import ngochung.app.Untils.SharedConfig;
import ngochung.app.chat_nodejs_android.R;

/**
 * Created by NGOCHUNG on 2/18/2017.
 */

public class ListFriendFragment extends Fragment {
    LitsfriendAdapters adapters;
    ListView lv;
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
        lv=(ListView)view.findViewById(R.id.lv_list_friend);
        GetAllFrend();

        return view;
    }
    public void GetAllFrend() {
        String access_token= new SharedConfig(getActivity()).getValueString(SharedConfig.ACCESS_TOKEN);
        try {
            APIConnection.getallfriend(getActivity(), access_token, new JSONObjectRequestListener() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        int code=response.getInt(Constants.CODE);
                        if(code==200){
                            JSONArray jsonArray= response.getJSONArray(Constants.RESULT);
                            ArrayList<Acounts> arr= new ArrayList<Acounts>();
                            if(jsonArray.length()!=0){
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Acounts ac = new Gson().fromJson(jsonArray.getJSONObject(i).toString(), Acounts.class);
                                    arr.add(ac);
                                }

                                    adapters= new LitsfriendAdapters(getActivity(),arr);
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
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();

    }


}