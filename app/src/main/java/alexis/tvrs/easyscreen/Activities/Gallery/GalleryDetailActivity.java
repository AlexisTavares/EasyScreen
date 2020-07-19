package alexis.tvrs.easyscreen.Activities.Gallery;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import alexis.tvrs.easyscreen.Activities.SplashActivity;
import alexis.tvrs.easyscreen.R;

public class GalleryDetailActivity extends AppCompatActivity {
    int position = 0;
    ImageView imageView;
    TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_detail);
        imageView = findViewById(R.id.gallery_detail_photo);
        textView = findViewById(R.id.gallery_detail_text);
        position = getIntent().getIntExtra("IMAGE",0);
        loadImage(position,imageView,textView);

    }

    public void PreviousImage(View view) {
        if(position > 0) {
            position--;
            loadImage(position,imageView,textView);
        }
    }

    public void NextImage(View view) {
        if(position < SplashActivity.PHONE_PHOTOS_PATHS.size()-1) {
            position++;
            loadImage(position,imageView,textView);
        }
    }

    private void loadImage(int position,ImageView imageView,TextView textView){
        String imagePath = SplashActivity.PHONE_PHOTOS_PATHS.get(position);
        textView.setText(imagePath);
        Picasso.get().load("https://picsum.photos/600/600")
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .fit()
                .centerCrop()
                .into(imageView);
    }
}
