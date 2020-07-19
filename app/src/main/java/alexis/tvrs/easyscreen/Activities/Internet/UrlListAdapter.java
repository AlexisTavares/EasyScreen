package alexis.tvrs.easyscreen.Activities.Internet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import alexis.tvrs.easyscreen.R;

public class UrlListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> list;

    public UrlListAdapter(Context context, ArrayList<String> list) {
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
            layoutItem = (LinearLayout) mInflater.inflate(R.layout.layout_url_list, parent, false);
        } else {
            layoutItem = (LinearLayout) convertView;
        }

        TextView urlText = (TextView) layoutItem.findViewById(R.id.layout_url_text);
        urlText.setText(list.get(position));

        return layoutItem;
    }
}
