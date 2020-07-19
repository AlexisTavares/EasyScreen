package alexis.tvrs.easyscreen.Activities.Communication.Message;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import alexis.tvrs.easyscreen.Activities.Contact.ContactGridActivity;
import alexis.tvrs.easyscreen.R;

import static alexis.tvrs.easyscreen.Activities.SplashActivity.PHONE_CONTACTS;

public class MessageWriteActivity extends AppCompatActivity {
    private ArrayList<Integer> contactPositions = new ArrayList<>();
    public static String ADD_CONTACT = "ADD_CONTACT";
    public static String ADD_DOCUMENT = "ADD_DOCUMENT";
    private static int ADD_CONTACT_REQUEST = 156;
    private static int ADD_DOCUMENT_REQUEST = 157;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_write);
        String origin = getIntent().getStringExtra("ORIGIN");
        if(origin!=null && origin.equals(MessageFullActivity.MESSAGE_READ_ANSWER)){
            int position = getIntent().getIntExtra("CONTACT_POSITION",-1);
            if(position>=0) {
                addContactToList(position);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CONTACT_REQUEST && resultCode == RESULT_OK) {
            int position = data.getIntExtra("CONTACT_POSITION",-1);
            addContactToList(position);
        }

        if (requestCode == ADD_DOCUMENT_REQUEST && resultCode == RESULT_OK) {

        }
    }

    private void addContactToList(int position){
        if(position > -1 && !contactPositions.contains(position)) {
            contactPositions.add(position);
            TextView contactList = findViewById(R.id.activity_message_write_contactList);
            String nameString = "";
            for (int contactPosition : contactPositions) {
                nameString += PHONE_CONTACTS.get(contactPosition).getName() + ";";
            }
            String placeholder = getString(R.string.sendTo) + ": " + nameString;
            contactList.setText(placeholder);
        }
    }
    public void addContact(View view) {
        Intent intent = new Intent(MessageWriteActivity.this, ContactGridActivity.class);
        intent.putExtra("ORIGIN",ADD_CONTACT);
        startActivityForResult(intent,ADD_CONTACT_REQUEST);
    }

    public void AddDocument(View view) {
        Intent intent = new Intent(MessageWriteActivity.this, ContactGridActivity.class);
        intent.putExtra("ORIGIN",ADD_DOCUMENT);
        startActivityForResult(intent,ADD_DOCUMENT_REQUEST);
    }

    public void SendMessage(View view) {
        SmsManager smsManager = SmsManager.getDefault();
        if(contactPositions.size()>=1) {
            EditText messageEditText = findViewById(R.id.activity_message_write_MessageField);
            String message = messageEditText.getText().toString();
            for (int contactPosition : contactPositions) {
                smsManager.sendTextMessage(PHONE_CONTACTS.get(contactPosition).getPhoneNumber(), null, message, null, null);
            }
            Toast.makeText(MessageWriteActivity.this, getString(R.string.messageSent), Toast.LENGTH_LONG).show();
            finish();
        }else{
            Toast.makeText(MessageWriteActivity.this, getString(R.string.noPhoneForSMS), Toast.LENGTH_LONG).show();
        }
    }
}
