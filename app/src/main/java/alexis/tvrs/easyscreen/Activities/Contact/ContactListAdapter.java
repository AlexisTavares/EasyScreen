package alexis.tvrs.easyscreen.Activities.Contact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import alexis.tvrs.easyscreen.R;

public class ContactListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Contact> list;

    public ContactListAdapter(Context context, ArrayList<Contact> list) {
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
            layoutItem = (LinearLayout) mInflater.inflate(R.layout.layout_contact_list, parent, false);
        } else {
            layoutItem = (LinearLayout) convertView;
        }

        ImageView icon = (ImageView) layoutItem.findViewById(R.id.layout_contact_list_image);
        Picasso.get().load(list.get(position).getPhotoURI()).resize(550, 550).centerCrop().into(icon);

        TextView name = (TextView) layoutItem.findViewById(R.id.layout_contact_list_name);
        name.setText(list.get(position).getName());

        return layoutItem;
    }
}
