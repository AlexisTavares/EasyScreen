package alexis.tvrs.easyscreen.Activities.Communication.Message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import alexis.tvrs.easyscreen.R;

public class MessageListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Message> list;

    public MessageListAdapter(Context context, ArrayList<Message> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layoutItem;
        LayoutInflater mInflater = LayoutInflater.from(context);

        if (convertView == null) {
            layoutItem = (LinearLayout) mInflater.inflate(R.layout.layout_message_list, parent, false);
        } else {
            layoutItem = (LinearLayout) convertView;
        }

        TextView messageFromView = (TextView) layoutItem.findViewById(R.id.layout_message_list_from);
        messageFromView.setText(list.get(position).getMessageFrom());

        TextView messageContentView = (TextView) layoutItem.findViewById(R.id.layout_message_list_content);
        messageContentView.setText(list.get(position).getMessageContent());

        return layoutItem;
    }
}
