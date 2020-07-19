package alexis.tvrs.easyscreen.Activities.Contact;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import alexis.tvrs.easyscreen.Activities.Communication.CommunicationActivity;
import alexis.tvrs.easyscreen.Activities.HomeActivity;
import alexis.tvrs.easyscreen.Activities.Communication.Message.MessageWriteActivity;
import alexis.tvrs.easyscreen.R;

import static alexis.tvrs.easyscreen.Activities.SplashActivity.PHONE_CONTACTS;

public class ContactGridActivity extends AppCompatActivity {
    private ContactGridAdapter contactGridAdapter;
    public static String CONTACT_CREATE = "CONTACT_CREATE";
    public static String CONTACT_EDIT = "CONTACT_EDIT";
    String origin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        origin = getIntent().getStringExtra("ORIGIN");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_grid);
        contactGridAdapter = new ContactGridAdapter(ContactGridActivity.this, PHONE_CONTACTS);

        GridView contactGridView = findViewById(R.id.activity_contact_grid_gridview);
        contactGridView.setAdapter(contactGridAdapter);

        if(origin.equals(HomeActivity.ORIGIN)) {
            contactGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ContactGridActivity.this, ContactEditActivity.class);
                    intent.putExtra("ORIGIN", CONTACT_EDIT);
                    intent.putExtra("CONTACT_POSITION", position);
                    startActivity(intent);
                }
            });
        }

        else if (origin.equals(CommunicationActivity.ORIGIN_PHONE)){
            contactGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(!PHONE_CONTACTS.get(position).getPhoneNumber().equals("") && PHONE_CONTACTS.get(position).getPhoneNumber()!=null) {
                        String number = PHONE_CONTACTS.get(position).getPhoneNumber();
                        String callString = "tel:" + number;

                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse(callString));
                        startActivity(intent);
                    }else{
                        Toast.makeText(ContactGridActivity.this,getResources().getString(R.string.no_phone_number_error),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        else if (origin.equals(MessageWriteActivity.ADD_CONTACT)){
            contactGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (!PHONE_CONTACTS.get(position).getPhoneNumber().equals("") && PHONE_CONTACTS.get(position).getPhoneNumber()!=null) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("CONTACT_POSITION", position);
                        setResult(MessageWriteActivity.RESULT_OK, resultIntent);
                        finish();
                    }else {
                        Toast.makeText(ContactGridActivity.this, R.string.no_phone_number_error,Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 888 && resultCode == RESULT_OK){
            int tempPos = data.getIntExtra("CONTACT_POSITION",-1);
            if(tempPos>=0) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("CONTACT_POSITION", tempPos);
                setResult(MessageWriteActivity.RESULT_OK, resultIntent);
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        contactGridAdapter.notifyDataSetChanged();
    }

    public void loadOtherContacts(View view) {
        Intent intent = new Intent(this, ContactListActivity.class);
        intent.putExtra("ORIGIN",origin);
        if (origin.equals(MessageWriteActivity.ADD_CONTACT)){
            startActivityForResult(intent,888);
        }else
        startActivity(intent);
    }

    public void createContact(View view) {
        Intent intent = new Intent(this, ContactEditActivity.class);
        intent.putExtra("ORIGIN",CONTACT_CREATE);
        startActivityForResult(intent,100);
    }
}
