package alexis.tvrs.easyscreen.Activities.Internet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import alexis.tvrs.easyscreen.R;
import alexis.tvrs.easyscreen.SharedPreferences.InternetUrlPreferences;

public class UrlSelectionActivity extends AppCompatActivity {
    InternetUrlPreferences internetUrlPreferences;
    ArrayList<String> urls;
    UrlListAdapter urlListAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url_selection);
        internetUrlPreferences = new InternetUrlPreferences(UrlSelectionActivity.this);
        urls = getIntent().getStringArrayListExtra("URLS");
        urlListAdapter = new UrlListAdapter(UrlSelectionActivity.this, urls);
        ListView urlListView = findViewById(R.id.activity_url_selection_listView);
        urlListView.setAdapter(urlListAdapter);
        checkEmptyList();
        urlListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("PAGE_POSITION", position);
                setResult(InternetActivity.RESULT_OK, resultIntent);
                finish();
            }
        });
        urlListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                urls.remove(position);
                urlListAdapter.notifyDataSetChanged();
                internetUrlPreferences.saveArrayList(urls);
                checkEmptyList();
                return true;
            }
        });
    }

    private void checkEmptyList(){
        if(urls.size() == 0){
            TextView no_favs = findViewById(R.id.activity_url_selection_no_url);
            no_favs.setVisibility(View.VISIBLE);
        }else{
            TextView no_favs = findViewById(R.id.activity_url_selection_no_url);
            no_favs.setVisibility(View.INVISIBLE);
        }
    }
}
