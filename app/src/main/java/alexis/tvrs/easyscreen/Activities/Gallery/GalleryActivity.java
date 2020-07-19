package alexis.tvrs.easyscreen.Activities.Gallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

import alexis.tvrs.easyscreen.Activities.SplashActivity;
import alexis.tvrs.easyscreen.R;

public class GalleryActivity extends AppCompatActivity {
    public static final int IMAGE_CAPTURE = 200;
    private GalleryGridAdapter galleryGridAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        galleryGridAdapter = new GalleryGridAdapter(GalleryActivity.this, SplashActivity.PHONE_PHOTOS_PATHS);
        GridView photoGridView = findViewById(R.id.activity_gallery_gridView);
        photoGridView.setAdapter(galleryGridAdapter);

        photoGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GalleryActivity.this, GalleryDetailActivity.class);
                intent.putExtra("IMAGE",position);
                startActivity(intent);
            }
        });
    }

    public void takePhoto(View view) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent,IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CAPTURE) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            Date currentTime = Calendar.getInstance().getTime();
            String ImagePath = MediaStore.Images.Media.insertImage(getContentResolver(), thumbnail, currentTime.toString(),null);
            SplashActivity.PHONE_PHOTOS_PATHS.add(ImagePath);
            galleryGridAdapter.notifyDataSetChanged();
        }

    }
}
