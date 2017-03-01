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

        final viewHoller holler= new viewHoller();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_list_invitation, parent, false);
            holler.phone = (TextView) convertView.findViewById(R.id.row_list_invitation_txt_numberphone);
            holler.name = (TextView) convertView.findViewById(R.id.row_list_invitation_txt_name);
            holler.bt_agree = (Button) convertView.findViewById(R.id.row_bt_list_invitation_ok);
            holler.bt_disagree = (Button) convertView.findViewById(R.id.row_bt_list_invitation_no);
            convertView.setTag(holler);
        }
        holler.phone.setText(data.get(position).getUser_id());
        holler.name.setText(data.get(position).getName());

        holler.bt_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("" + position);
                replyinvition(data.get(position).getName(),data.get(position).getUser_id(), 1,holler);
            }
        });

        holler.bt_disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("" + position);
                replyinvition(data.get(position).getName(),data.get(position).getUser_id(), 2,holler);
            }
        });
        return convertView;
    }

    public void replyinvition(String name, String friend_id, final int status, final viewHoller holler) {
        try {
            APIConnection.friendreply(mContext, name, friend_id, status, new JSONObjectRequestListener() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        int code = response.getInt(Constants.CODE);

                        if (code == 200) {
                            if (status == 1) {
                                holler.bt_disagree.setVisibility(View.GONE);
                                holler.bt_agree.setText(mContext.getResources().getString(R.string.friend));


                            } else {
                                holler.bt_agree.setVisibility(View.GONE);
                                holler.bt_disagree.setText(mContext.getResources().getString(R.string.watched));
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

    public class viewHoller{
         TextView phone, name;
         Button bt_agree, bt_disagree;
    }
}
