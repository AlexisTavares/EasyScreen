package alexis.tvrs.easyscreen.Activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import alexis.tvrs.easyscreen.Activities.Contact.Contact;
import alexis.tvrs.easyscreen.Activities.Communication.Message.Message;
import alexis.tvrs.easyscreen.R;
import alexis.tvrs.easyscreen.Utils.PermissionsUtil;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.SEND_SMS;
import static android.Manifest.permission.WRITE_CONTACTS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SplashActivity extends AppCompatActivity {
    private static final int PERMISSION_ALL = 0;
    private Handler h;
    private Runnable r;

    public static ArrayList<Contact> PHONE_CONTACTS = new ArrayList<>();
    public static ArrayList<Message> PHONE_MESSAGES = new ArrayList<>();
    public static ArrayList<String> PHONE_PHOTOS_PATHS = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        h = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                PHONE_CONTACTS = loadPhoneContacts();
                PHONE_MESSAGES = loadPhoneSMS();
                PHONE_PHOTOS_PATHS = loadPhonePhotos();

                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        };

        String[] PERMISSIONS = {
                READ_CONTACTS,
                WRITE_CONTACTS,
                READ_SMS,
                SEND_SMS,
                READ_EXTERNAL_STORAGE,
                WRITE_EXTERNAL_STORAGE,
                INTERNET,
                ACCESS_NETWORK_STATE,
        };

        if(!PermissionsUtil.hasPermissions(this, PERMISSIONS))
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        else
            h.postDelayed(r, 1500);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        int index = 0;
        Map<String, Integer> PermissionsMap = new HashMap<>();
        for (String permission : permissions){
            PermissionsMap.put(permission, grantResults[index]);
            index++;
        }

        if((PermissionsMap.get(READ_CONTACTS) != 0)
                || PermissionsMap.get(READ_SMS) != 0
                || PermissionsMap.get(SEND_SMS) != 0
                || PermissionsMap.get(READ_EXTERNAL_STORAGE) != 0
                || PermissionsMap.get(WRITE_EXTERNAL_STORAGE) != 0
                || PermissionsMap.get(INTERNET) != 0
                || PermissionsMap.get(ACCESS_NETWORK_STATE) != 0){
            startActivity(new Intent(SplashActivity.this,NoPermissionsActivity.class));
            finish();
        }
        else
        {
            h.postDelayed(r, 1500);
        }
    }

    private ArrayList<Contact> loadPhoneContacts() {
        ArrayList<Contact> tempContacts = new ArrayList<>();
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                Contact tempContact = new Contact();

                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                tempContact.setId(id);

                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                tempContact.setName(name);

                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[] { id }, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        tempContact.setPhoneNumber(phoneNo);
                    }
                    pCur.close();

                    Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
                    while (emailCur.moveToNext()) {
                        String email = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        tempContact.setEmailAddress(email);
                    }
                    emailCur.close();

                    Cursor postalCur = cr.query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
                    while (postalCur.moveToNext()) {
                        String postal = postalCur.getString(postalCur.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS));
                        tempContact.setPostalAddress(postal);
                    }
                    postalCur.close();
                }


                String image_uri = cur.getString(cur.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

                if (image_uri != null) {
                    tempContact.setPhotoURI(Uri.parse(image_uri));
                }else{
                    tempContact.setPhotoURI(null);
                }
                tempContacts.add(tempContact);
            }
        }
        return tempContacts;
    }

    private ArrayList<Message> loadPhoneSMS() {
        ArrayList<Message> tempMessages = new ArrayList<>();
        Cursor cur = getContentResolver().query(Uri.parse("content://sms/inbox"),
                null,
                null,
                null,
                null);
        if (cur.getCount() > 0) {
            int indexBody = cur.getColumnIndex("body");
            int indexAddress = cur.getColumnIndex("address");
            while (cur.moveToNext()) {
                Message tempMessage = new Message();
                tempMessage.setMessageContent(cur.getString(indexBody));
                tempMessage.setMessageFrom(cur.getString(indexAddress));
                tempMessages.add(tempMessage);
            }
        }
        return tempMessages;
    }

    private ArrayList<String> loadPhonePhotos(){
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listOfAllImages = new ArrayList<>();
        String absolutePathOfImage;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME };
        cursor = getContentResolver().query(uri,
                projection,
                null,
                null,
                null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            listOfAllImages.add(absolutePathOfImage);
        }
        return listOfAllImages;
    }

}