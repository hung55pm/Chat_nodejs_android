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

import ngochung.app.Connect.APIConnection;
import ngochung.app.Connect.JSONObjectRequestListener;
import ngochung.app.Constants.Constants;
import ngochung.app.Models.Acounts;
import ngochung.app.Untils.SharedConfig;
import ngochung.app.chat_nodejs_android.R;

/**
 * Created by MR_HUNG on 2/19/2017.
 */

public class InvitationAdapters extends BaseAdapter {
    private TextView phone, name;
    private Button bt_agree, bt_disagree;
    private Context mContext;
    private ArrayList<Acounts> data;
    private LayoutInflater inflater = null;

    public InvitationAdapters(Context mContext, ArrayList<Acounts> data) {
        this.mContext = mContext;
        this.data = data;
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
            convertView = inflater.inflate(R.layout.row_list_invitation, parent, false);
            phone = (TextView) convertView.findViewById(R.id.row_list_invitation_txt_numberphone);
            name = (TextView) convertView.findViewById(R.id.row_list_invitation_txt_name);
            bt_agree = (Button) convertView.findViewById(R.id.row_bt_list_invitation_ok);
            bt_disagree = (Button) convertView.findViewById(R.id.row_bt_list_invitation_no);

        }
        phone.setText(data.get(position).getUser_id());
        name.setText(data.get(position).getName());

        bt_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("" + position);
                replyinvition(data.get(position).getUser_id(), 1, position);
            }
        });

        bt_disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("" + position);
                replyinvition(data.get(position).getUser_id(), 2, position);
            }
        });
        return convertView;
    }

    public void replyinvition(String friend_id, final int status, final int po) {
        String access_token = new SharedConfig(mContext).getValueString(SharedConfig.ACCESS_TOKEN);
        try {
            APIConnection.friendreply(mContext, friend_id, status, access_token, new JSONObjectRequestListener() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        int code = response.getInt(Constants.CODE);

                        if (code == 200) {
                            if (status == 1) {

                                bt_disagree.setVisibility(View.GONE);
                                bt_agree.setText(mContext.getResources().getString(R.string.friend));


                            } else {
                                bt_agree.setVisibility(View.GONE);
                                bt_disagree.setText(mContext.getResources().getString(R.string.watched));
                            }
                        } else {
                            showToast(mContext.getResources().getString(R.string.retrieve_data_error));
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

    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();

    }
}
