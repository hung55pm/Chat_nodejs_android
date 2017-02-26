package ngochung.app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ngochung.app.Models.MesRecent;
import ngochung.app.Models.Message;
import ngochung.app.chat_nodejs_android.R;

/**
 * Created by MR_HUNG on 2/26/2017.
 */

public class ListMesRecentAdapters extends BaseAdapter {
    TextView name,mes,date;
    ArrayList<MesRecent> data;
    Context mContext;
    LayoutInflater inflater=null;
    public ListMesRecentAdapters(Context mContext, ArrayList<MesRecent> data) {
        this.mContext=mContext;
        this.data=data;
        inflater=(LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
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
        if(convertView==null){
            convertView=inflater.inflate(R.layout.row_list_mes_recent,parent,false);
             name=(TextView)convertView.findViewById(R.id.row_list_mes_recent_textname);
             mes=(TextView)convertView.findViewById(R.id.row_list_mes_recent_message);
             date=(TextView)convertView.findViewById(R.id.row_list_mes_recent_date);

        }
        name.setText(data.get(position).getFriend_name());
        mes.setText(data.get(position).getSender_name()+": "+data.get(position).getMessage());
        date.setText(data.get(position).getDate().toString());

        return convertView;
    }
}
