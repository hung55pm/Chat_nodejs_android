package ngochung.app.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ngochung.app.Models.Message;
import ngochung.app.Untils.SharedConfig;
import ngochung.app.chat_nodejs_android.R;

/**
 * Created by NGOCHUNG on 2/22/2017.
 */

public class MessagesListAdapters extends BaseAdapter {

    private Context context;
    private List<Message> messagesItems;

    public MessagesListAdapters(Context context, List<Message> navDrawerItems) {
        this.context = context;
        this.messagesItems = navDrawerItems;
    }

    @Override
    public int getCount() {
        return messagesItems.size();
    }

    @Override
    public Object getItem(int position) {
        return messagesItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /**
         * The following list not implemented reusable list items as list items
         * are showing incorrect data Add the solution if you have one
         * */
        String user_id= new SharedConfig(context).getValueString(SharedConfig.USER_ID);
        Message m = messagesItems.get(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        // Identifying the message owner
        if (messagesItems.get(position).getUser_id().equals(user_id)) {
            // message belongs to you, so load the right aligned layout
            convertView = mInflater.inflate(R.layout.list_item_message_right,
                    null);
        } else {
            // message belongs to other person, load the left aligned layout
            convertView = mInflater.inflate(R.layout.list_item_message_left,
                    null);
        }

        TextView txtMsg = (TextView) convertView.findViewById(R.id.txtMsg);

        txtMsg.setText(m.getMessage());

        return convertView;
    }
}