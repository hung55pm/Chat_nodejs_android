package ngochung.app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ngochung.app.Models.Acounts;
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
    private LayoutInflater inflater=null;

    public SearchFriendAdapters(Context mContext, ArrayList<Acounts> data) {
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
            convertView = inflater.inflate(R.layout.row_search_add_friend, parent,false);
            txt_name = (TextView) convertView.findViewById(R.id.row_addfriend_txt_name);
            txt_phone = (TextView) convertView.findViewById(R.id.row_addfriend_txt_numberphone);
            bt_add = (Button) convertView.findViewById(R.id.row_bt_add_friend);
        }
        txt_name.setText(data.get(position).getName());
        txt_phone.setText(data.get(position).getUser_id());

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("add friend: " + position);
            }
        });

        return convertView;
    }

    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();

    }
}
