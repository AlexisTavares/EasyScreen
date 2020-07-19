package alexis.tvrs.easyscreen.Activities.Communication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import alexis.tvrs.easyscreen.Activities.Contact.ContactGridActivity;
import alexis.tvrs.easyscreen.Activities.Communication.Message.MessageWriteActivity;
import alexis.tvrs.easyscreen.R;

public class CommunicationActivity extends AppCompatActivity {
    public static String ORIGIN_PHONE = "COMMUNICATION_ACTIVITY_PHONE";
    public static String ORIGIN_MESSAGE = "COMMUNICATION_ACTIVITY_MESSAGE";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);
    }

    public void startMessageWriting(View view) {
        Intent intent = new Intent(this, MessageWriteActivity.class);
//        intent.putExtra("ORIGIN",ORIGIN_MESSAGE);
        startActivity(intent);
    }

    public void startEmailWriting(View view) {
    }

    public void startPhoneCompose(View view) {
        Intent intent = new Intent(this,DialActivity.class);
        startActivity(intent);
    }

    public void startPhoneCall(View view) {
        Intent intent = new Intent(this, ContactGridActivity.class);
        intent.putExtra("ORIGIN",ORIGIN_PHONE);
        startActivity(intent);
    }


}
