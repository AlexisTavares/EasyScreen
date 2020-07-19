package alexis.tvrs.easyscreen.Activities.Contact;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import alexis.tvrs.easyscreen.Activities.Communication.CommunicationActivity;
import alexis.tvrs.easyscreen.Activities.Communication.Message.MessageWriteActivity;
import alexis.tvrs.easyscreen.Activities.HomeActivity;
import alexis.tvrs.easyscreen.R;
import alexis.tvrs.easyscreen.SharedPreferences.Preferences;

import static alexis.tvrs.easyscreen.Activities.SplashActivity.PHONE_CONTACTS;

public class ContactListActivity extends AppCompatActivity {
    private ContactListAdapter contactListAdapter;
    private Preferences prefs = new Preferences(ContactListActivity.this);
    public static String CONTACT_CREATE = "CONTACT_CREATE";
    public static String CONTACT_EDIT = "CONTACT_EDIT";
    String origin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        origin = getIntent().getStringExtra("ORIGIN");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        contactListAdapter = new ContactListAdapter(ContactListActivity.this, PHONE_CONTACTS);

        ListView contactListView = findViewById(R.id.activity_contact_list_list);
        contactListView.setAdapter(contactListAdapter);

        if(origin.equals(HomeActivity.ORIGIN)) {
            contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ContactListActivity.this, ContactEditActivity.class);
                    intent.putExtra("ORIGIN", CONTACT_EDIT);
                    intent.putExtra("CONTACT_POSITION", position);
                    startActivity(intent);
                }
            });
        }
        else if (origin.equals(CommunicationActivity.ORIGIN_PHONE)){
            contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(PHONE_CONTACTS.get(position).getPhoneNumber()!=null) {
                        String number = PHONE_CONTACTS.get(position).getPhoneNumber();
                        String callString = "tel:" + number;

                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse(callString));
                        startActivity(intent);
                    }
                }
            });
        }

        else if (origin.equals(CommunicationActivity.ORIGIN_MESSAGE)){
            contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(!PHONE_CONTACTS.get(position).getPhoneNumber().equals("") && PHONE_CONTACTS.get(position).getPhoneNumber()!=null) {
                        String number = PHONE_CONTACTS.get(position).getPhoneNumber();
                        String callString = "tel:" + number;

                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse(callString));
                        startActivity(intent);
                    }
                }
            });
        }
        else if (origin.equals(MessageWriteActivity.ADD_CONTACT)) {
            contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (!PHONE_CONTACTS.get(position).getPhoneNumber().equals("") && PHONE_CONTACTS.get(position).getPhoneNumber() != null) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("CONTACT_POSITION", position);
                        setResult(ContactGridActivity.RESULT_OK, resultIntent);
                        finish();
                    } else {
                        Toast.makeText(ContactListActivity.this, R.string.no_phone_number_error, Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        contactListAdapter.notifyDataSetChanged();
    }

    public void createContact(View view) {
        Intent intent = new Intent(this, ContactEditActivity.class);
        intent.putExtra("ORIGIN",CONTACT_CREATE);
        startActivityForResult(intent,100);
    }
}
