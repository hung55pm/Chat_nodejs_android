package ngochung.app.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ngochung.app.Adapters.SearchFriendAdapters;
import ngochung.app.Applications.MyApplication;
import ngochung.app.Connect.APIConnection;
import ngochung.app.Connect.JSONObjectRequestListener;
import ngochung.app.Constants.Constants;
import ngochung.app.Models.Acounts;
import ngochung.app.Untils.SharedConfig;
import ngochung.app.chat_nodejs_android.MainActivity;
import ngochung.app.chat_nodejs_android.R;

/**
 * Created by NGOCHUNG on 2/17/2017.
 */

public class SearchFragment extends Fragment {
    Context mContext;
    public static String SFLOG="SearchFragment";
    private SearchFriendAdapters adapters;
    private ListView lv;
    private TextView txt_title_tb;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        mContext=getActivity();
        lv=(ListView)view.findViewById(R.id.lv_result_search_friend) ;
        txt_title_tb=(TextView)view.findViewById(R.id.txt_thongbao);
        if(MyApplication.CHECK_SEARCH==true){
            search(MyApplication.PHONE_KEY_SEARCH_FRIEND);
            MyApplication.CHECK_SEARCH=false;
        }

//        Log.i(SFLOG +" phone",MyApplication.PHONE_KEY_SEARCH_FRIEND);
        return view;
    }

    public void search(String phone){
        String access_token=new SharedConfig(mContext).getValueString(SharedConfig.ACCESS_TOKEN);
        try {
            APIConnection.searchfriend(mContext, phone,access_token, new JSONObjectRequestListener() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        int code=response.getInt(Constants.CODE);
                        if(code==200|| code==300){
                            JSONArray jsonArray= response.getJSONArray(Constants.RESULT);
                            ArrayList<Acounts> arr= new ArrayList<Acounts>();
                            if(jsonArray.length()!=0){
                                txt_title_tb.setVisibility(View.GONE);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Acounts ac = new Gson().fromJson(jsonArray.getJSONObject(i).toString(), Acounts.class);
                                    arr.add(ac);
                                }
                                if (code == 200) {
                                    adapters= new SearchFriendAdapters(mContext,arr,false);
                                    lv.setAdapter(adapters);
                                }else {
                                    adapters= new SearchFriendAdapters(mContext,arr,true);
                                    lv.setAdapter(adapters);
                                }


                            }else {
                                txt_title_tb.setVisibility(View.VISIBLE);
                                txt_title_tb.setText(getResources().getString(R.string.not_found));
                            }
                        }else {
                            showToast(getResources().getString(R.string.err));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.i(SFLOG,response.toString());
                }

                @Override
                public void onError(VolleyError error) {
                    Log.i(SFLOG+"   err",""+error.toString());
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void showToast(String msg){
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();

    }
}