package ngochung.app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

public class SearchFriendAdapters extends BaseAdapter {
    private ArrayList<Acounts> data;
    private Context mContext;
    private TextView txt_name, txt_phone;
    private Button bt_add;
    private Boolean check;
    private LayoutInflater inflater=null;

    public SearchFriendAdapters(Context mContext, ArrayList<Acounts> data,Boolean check) {
        this.mContext = mContext;
        this.data = data;
        this.check=check;
        inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_search_add_friend, parent,false);
            txt_name = (TextView) convertView.findViewById(R.id.row_addfriend_txt_name);
            txt_phone = (TextView) convertView.findViewById(R.id.row_addfriend_txt_numberphone);
            bt_add = (Button) convertView.findViewById(R.id.row_bt_add_friend);
        }
        txt_name.setText(data.get(position).getName());
        txt_phone.setText(data.get(position).getUser_id());
        if(check==true){
            bt_add.setText(mContext.getResources().getString(R.string.sent_invitations));
            bt_add.setEnabled(false);
        }else {
            bt_add.setText(mContext.getResources().getString(R.string.add_friend));
            bt_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    add_friends(data.get(position).getUser_id(),data.get(position).getName());
                    showToast("add friend: " + position);
                }
            });
        }

        return convertView;
    }

    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();

    }
    public void add_friends(String phone, String name){
        String access=new SharedConfig(mContext).getValueString(SharedConfig.ACCESS_TOKEN);
        try {
            APIConnection.addfriend(mContext, phone,name, access, new JSONObjectRequestListener() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        int code =response.getInt(Constants.CODE);
                        if(code==200){
                            showToast("add friend success");
                            bt_add.setText(mContext.getResources().getString(R.string.sent_invitations));
                            bt_add.setEnabled(false);
                        }else {
                            showToast("add friend fail");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        showToast(mContext.getResources().getString(R.string.err));
                    }
                }

                @Override
                public void onError(VolleyError error) {
                    showToast(mContext.getResources().getString(R.string.err_voley));
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
