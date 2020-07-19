package alexis.tvrs.easyscreen.Activities.Gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import alexis.tvrs.easyscreen.R;

public class GalleryGridAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> list;

    public GalleryGridAdapter(Context context, ArrayList<String> list) {
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
            layoutItem = (LinearLayout) mInflater.inflate(R.layout.layout_gallery_grid, parent, false);
        } else {
            layoutItem = (LinearLayout) convertView;
        }

        ImageView image = layoutItem.findViewById(R.id.layout_gallery_image);

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(list.get(position)));
            image.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
            Picasso.get().load("https://picsum.photos/600/600")
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .fit()
                    .centerCrop()
                    .into(image);
        }

//        TextView gfgjgnfsj = layoutItem.findViewById(R.id.debug_filepath);
//        gfgjgnfsj.setText(list.get(position));

        return layoutItem;
    }

}
