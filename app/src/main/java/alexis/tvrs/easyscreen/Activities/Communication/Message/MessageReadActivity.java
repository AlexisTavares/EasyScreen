package alexis.tvrs.easyscreen.Activities.Communication.Message;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import alexis.tvrs.easyscreen.Activities.SplashActivity;
import alexis.tvrs.easyscreen.R;

public class MessageReadActivity extends AppCompatActivity {
    private MessageListAdapter messageListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_read);
        messageListAdapter = new MessageListAdapter(MessageReadActivity.this, SplashActivity.PHONE_MESSAGES);
        if(SplashActivity.PHONE_MESSAGES.size() <= 0){
            TextView NoMessageView = findViewById(R.id.activity_message_read_NoMessagesView);
            NoMessageView.setVisibility(View.VISIBLE);
        }
        ListView messageListView = findViewById(R.id.activity_message_read_listView);
        messageListView.setAdapter(messageListAdapter);

        messageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MessageReadActivity.this,MessageFullActivity.class);
                intent.putExtra("MESSAGE_POSITION",position);
                startActivity(intent);
            }
        });
    }



}
