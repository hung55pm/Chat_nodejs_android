package ngochung.app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ngochung.app.Models.Acounts;
import ngochung.app.chat_nodejs_android.R;

/**
 * Created by NGOCHUNG on 2/20/2017.
 */

public class LitsfriendAdapters extends BaseAdapter {
    private Context mContext;
    private ArrayList<Acounts> data;
    private LayoutInflater inflater = null;
    public LitsfriendAdapters(Context mContext, ArrayList<Acounts> data) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=new ViewHolder();
        if(convertView==null){
            convertView=inflater.inflate(R.layout.row_list_friend,parent,false);
            viewHolder.txt_name=(TextView)convertView.findViewById(R.id.row_list_friend_txt_name);
            viewHolder.txt_phone=(TextView)convertView.findViewById(R.id.row_list_friend_txt_numberphone);
        }
        viewHolder.txt_name.setText(data.get(position).getName());
        viewHolder.txt_phone.setText(data.get(position).getUser_id());
        return convertView;
    }

    public class ViewHolder{
        TextView txt_name,txt_phone;

    }
}
