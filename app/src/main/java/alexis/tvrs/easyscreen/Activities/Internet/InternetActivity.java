package alexis.tvrs.easyscreen.Activities.Internet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Set;

import alexis.tvrs.easyscreen.R;
import alexis.tvrs.easyscreen.SharedPreferences.InternetUrlPreferences;

public class InternetActivity extends AppCompatActivity {
    WebView webView;
    public static final int REQUEST_PAGE_POSITION = 400;
    InternetUrlPreferences internetUrlPreferences;
    ArrayList<String> favoriteUrls;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);

        internetUrlPreferences = new InternetUrlPreferences(InternetActivity.this);
        favoriteUrls = internetUrlPreferences.getArrayList();
        if(favoriteUrls == null){
            favoriteUrls = new ArrayList<>();
            internetUrlPreferences.saveArrayList(favoriteUrls);
        }

        webView = findViewById(R.id.activity_internet_webView);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.loadUrl("https://www.google.co.in");
    }

    public void addToFavorite(View view) {
        String url = webView.getUrl();
        if(!favoriteUrls.contains(url)) {
            favoriteUrls.add(webView.getUrl());
            internetUrlPreferences.saveArrayList(favoriteUrls);
            Toast.makeText(InternetActivity.this, getString(R.string.url_saved), Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(InternetActivity.this, getString(R.string.existing_url), Toast.LENGTH_SHORT).show();
    }

    public void loadFavoritePages(View view) {
        Intent intent = new Intent(InternetActivity.this,UrlSelectionActivity.class);
        intent.putExtra("URLS", favoriteUrls);
        startActivityForResult(intent,REQUEST_PAGE_POSITION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == REQUEST_PAGE_POSITION){
            int position = data.getIntExtra("PAGE_POSITION",-1);
            if(position>=0) {
                webView.loadUrl(favoriteUrls.get(position));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        favoriteUrls = internetUrlPreferences.getArrayList();
    }
}
