package alexis.tvrs.easyscreen.Activities.Communication.Message;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import alexis.tvrs.easyscreen.Activities.Contact.Contact;
import alexis.tvrs.easyscreen.Activities.SplashActivity;
import alexis.tvrs.easyscreen.R;

public class MessageFullActivity extends AppCompatActivity {
    public static final String MESSAGE_READ_ANSWER = "MESSAGE_READ_ANSWER";
    TextView fromText;
    TextView messageText;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_full);
        fromText = findViewById(R.id.message_full_activity_from);
        messageText = findViewById(R.id.message_full_activity_message);
        int message_position = getIntent().getIntExtra("MESSAGE_POSITION",-1);
        if(message_position>=0){
            messageText.setText(SplashActivity.PHONE_MESSAGES.get(message_position).getMessageContent());
            fromText.setText(SplashActivity.PHONE_MESSAGES.get(message_position).getMessageFrom());
        }
    }

    public void AnswerMessage(View view) {
        if(!fromText.getText().toString().equals("")){
            String phoneNumber = fromText.getText().toString();
            int contactPosition = -1;
            for (Contact contact: SplashActivity.PHONE_CONTACTS) {
                if(contact.getPhoneNumber().equals(phoneNumber)){
                    contactPosition = SplashActivity.PHONE_CONTACTS.indexOf(contact);
                }
            }
            Intent intent = new Intent(MessageFullActivity.this,MessageWriteActivity.class);
            if(contactPosition>=0) {
                intent.putExtra("ORIGIN",MESSAGE_READ_ANSWER);
                intent.putExtra("CONTACT_POSITION", contactPosition);
            }
            startActivity(intent);
        }
    }
}
