package alexis.tvrs.easyscreen.Activities.Contact;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import alexis.tvrs.easyscreen.R;

import static alexis.tvrs.easyscreen.Activities.SplashActivity.PHONE_CONTACTS;

public class ContactEditActivity extends AppCompatActivity {
    private boolean modifyState;
    private Button saveButton;
    private String origin;
    private Contact contact;
    private Contact tempContact;
    private ImageView icon;
    private TextView name;
    private TextView phoneNumber;
    private TextView email;
    private TextView postalAddress;
    private int GALLERY = 1, CAMERA = 2;
    private static final int CONTACT_SAVE_TO_PHONE = 301;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        Button modifyButton = findViewById(R.id.activity_edit_contact_modify);
        saveButton = findViewById(R.id.activity_edit_contact_save);
        Button deleteButton = findViewById(R.id.activity_edit_contact_delete);
        deleteButton.setVisibility(View.INVISIBLE);
        saveButton.setVisibility(View.INVISIBLE);

        icon = findViewById(R.id.activity_edit_contact_image);
        name = findViewById(R.id.activity_edit_contact_name);
        phoneNumber = findViewById(R.id.activity_edit_contact_phone_number);
        email = findViewById(R.id.activity_edit_contact_email);
        postalAddress = findViewById(R.id.activity_edit_contact_postal_address);
        origin = getIntent().getStringExtra("ORIGIN");

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyState = !modifyState;
                toggleModify();
            }
        });

        if(origin.equals("CONTACT_CREATE")){
            modifyState = true;
            toggleModify();
            modifyButton.setVisibility(View.INVISIBLE);
            saveButton.setVisibility(View.VISIBLE);

            icon = findViewById(R.id.activity_edit_contact_image);
        }
        else if(origin.equals("CONTACT_EDIT")){
            modifyState = false;
            toggleModify();
            deleteButton.setVisibility(View.VISIBLE);
            int position = getIntent().getIntExtra("CONTACT_POSITION",0);
            contact = PHONE_CONTACTS.get(position);

            try {
                Bitmap bitmap;
                if(contact.getPhotoURI()!=null){
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contact.getPhotoURI());
                    icon.setImageBitmap(bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            name.setText(contact.getName());
            phoneNumber.setText(contact.getPhoneNumber());
            email.setText(contact.getEmailAddress());
            postalAddress.setText(contact.getPostalAddress());
        }
    }

    public void saveContact(View view) {
        if(!name.getText().toString().equals("")) {
            if (origin.equals(ContactGridActivity.CONTACT_EDIT)){
//                long rawContactId = getRawContactIdByName(contact.getName());
//                ContentResolver contentResolver = getContentResolver();
//                updatePhoneNumber(contentResolver,rawContactId);

                if(icon.getContentDescription()!=null) {
                    contact.setPhotoURI(Uri.parse(icon.getContentDescription().toString()));
                }
                contact.setName(name.getText().toString());
                contact.setPhoneNumber(phoneNumber.getText().toString());
                contact.setEmailAddress(email.getText().toString());
                contact.setPostalAddress(postalAddress.getText().toString());
                finish();
            }

            else if (origin.equals(ContactGridActivity.CONTACT_CREATE)){
                    tempContact = new Contact();
                    if(icon.getContentDescription()!=null) {
                        tempContact.setPhotoURI(Uri.parse(icon.getContentDescription().toString()));
                    }
                    tempContact.setName(name.getText().toString());
                    tempContact.setPhoneNumber(phoneNumber.getText().toString());
                    tempContact.setEmailAddress(email.getText().toString());
                    tempContact.setPostalAddress(postalAddress.getText().toString());
                    PHONE_CONTACTS.add(tempContact);

                    String displayName = name.getText().toString();
                    String mobileNumber = phoneNumber.getText().toString();
                    String emailUser = email.getText().toString();

                    ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

                    ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                            .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                            .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null).build());

                    if (displayName != null) {
                        ops.add(ContentProviderOperation
                                .newInsert(ContactsContract.Data.CONTENT_URI)
                                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                                .withValue(ContactsContract.Data.MIMETYPE,
                                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                                        displayName).build());
                    }

                    if (mobileNumber != null) {
                        ops.add(ContentProviderOperation
                                .newInsert(ContactsContract.Data.CONTENT_URI)
                                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                                .withValue(ContactsContract.Data.MIMETYPE,
                                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, mobileNumber)
                                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE).build());
                    }

                    if (email != null) {
                        ops.add(ContentProviderOperation
                                .newInsert(ContactsContract.Data.CONTENT_URI)
                                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                                .withValue(ContactsContract.Data.MIMETYPE,
                                        ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                                .withValue(ContactsContract.CommonDataKinds.Email.DATA, emailUser)
                                .withValue(ContactsContract.CommonDataKinds.Email.TYPE,
                                        ContactsContract.CommonDataKinds.Email.TYPE_WORK).build());
                    }

                    try {
                        getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

            }
            Toast.makeText(ContactEditActivity.this,getResources().getString(R.string.contact_save),Toast.LENGTH_LONG).show();
            finish();
        }else{
            Toast.makeText(ContactEditActivity.this,getResources().getString(R.string.no_name_specified),Toast.LENGTH_LONG).show();
        }
    }

    private void updatePhoneNumber(ContentResolver contentResolver, long rawContactId) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,name.getText().toString());
        contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber.getText().toString());
        contentValues.put(ContactsContract.CommonDataKinds.Email.DATA,email.getText().toString());
        contentValues.put(ContactsContract.CommonDataKinds.StructuredPostal.STREET,postalAddress.getText().toString());

        StringBuffer whereClauseBuf = new StringBuffer();

        whereClauseBuf.append(ContactsContract.Data.RAW_CONTACT_ID);
        whereClauseBuf.append("=");
        whereClauseBuf.append(rawContactId);

        whereClauseBuf.append(" and ");
        whereClauseBuf.append(ContactsContract.Data.MIMETYPE);
        whereClauseBuf.append(" = '");
        String mimetype = ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE;
        whereClauseBuf.append(mimetype);
        whereClauseBuf.append("'");

        Uri contactUri = ContactsContract.Data.CONTENT_URI;
        contentResolver.update(contactUri, contentValues, whereClauseBuf.toString(), null);
    }

    public void deleteContact(View view) {
        String name = contact.getName();
        long rawContactId = getRawContactIdByName(name);
        ContentResolver contentResolver = getContentResolver();
        Uri dataContentUri = ContactsContract.Data.CONTENT_URI;
        String dataWhereClause = ContactsContract.Data.RAW_CONTACT_ID + " = " + rawContactId;
        contentResolver.delete(dataContentUri, dataWhereClause, null);
        Uri rawContactUri = ContactsContract.RawContacts.CONTENT_URI;
        String rawContactWhereClause = ContactsContract.RawContacts._ID + " = " + rawContactId;
        contentResolver.delete(rawContactUri, rawContactWhereClause, null);
        Uri contactUri = ContactsContract.Contacts.CONTENT_URI;
        String contactWhereClause = ContactsContract.Contacts._ID + " = " + rawContactId;
        contentResolver.delete(contactUri, contactWhereClause, null);

        Toast.makeText(ContactEditActivity.this,getResources().getString(R.string.contact_delete),Toast.LENGTH_LONG).show();
        PHONE_CONTACTS.remove(contact);
        finish();
    }

    private void toggleModify() {
        if(modifyState){
            icon.setClickable(true);
            saveButton.setVisibility(View.VISIBLE);
            name.setFocusable(true);
            name.setFocusableInTouchMode(true);
            phoneNumber.setFocusable(true);
            phoneNumber.setFocusableInTouchMode(true);
            email.setFocusable(true);
            email.setFocusableInTouchMode(true);
            postalAddress.setFocusable(true);
            postalAddress.setFocusableInTouchMode(true);
        }
        else {
            icon.setClickable(false);
            saveButton.setVisibility(View.INVISIBLE);
            name.setFocusable(false);
            name.setFocusableInTouchMode(false);
            phoneNumber.setFocusable(false);
            phoneNumber.setFocusableInTouchMode(false);
            email.setFocusable(false);
            email.setFocusableInTouchMode(false);
            postalAddress.setFocusable(false);
            postalAddress.setFocusableInTouchMode(false);
        }
    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle(getString(R.string.get_image_from)+":");
        String[] pictureDialogItems = {
                getString(R.string.get_image_gallery),
                getString(R.string.take_photo) };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallery();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    private void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (requestCode == GALLERY) {
            Uri contentURI = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                icon.setContentDescription(contentURI.toString());
                icon.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == CAMERA) {
            Uri contentURI = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                icon.setContentDescription(contentURI.toString());
                icon.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private long getRawContactIdByName(String displayName) {
        ContentResolver contentResolver = getContentResolver();

        // Create query column array.
        String[] queryColumnArr = {ContactsContract.RawContacts._ID};

        // Create where condition clause.
        String whereClause = ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY + " = '" + displayName + "'";

        // Query raw contact id through RawContacts uri.
        Uri rawContactUri = ContactsContract.RawContacts.CONTENT_URI;

        // Return the query cursor.
        Cursor cursor = contentResolver.query(rawContactUri, queryColumnArr, whereClause, null, null);

        long rawContactId = -1;

        if(cursor!=null)
        {
            // Get contact count that has same display name, generally it should be one.
            int queryResultCount = cursor.getCount();
            // This check is used to avoid cursor index out of bounds exception. android.database.CursorIndexOutOfBoundsException
            if(queryResultCount > 0)
            {
                // Move to the first row in the result cursor.
                cursor.moveToFirst();
                // Get raw_contact_id.
                rawContactId = cursor.getLong(cursor.getColumnIndex(ContactsContract.RawContacts._ID));
            }
        }

        return rawContactId;
    }
}