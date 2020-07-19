package alexis.tvrs.easyscreen.Activities.Contact;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;

public class Contact implements Serializable {
    private String id;
    private String name;
    private String phoneNumber;
    private String emailAddress;
    private String postalAddress;
    private Uri photoURI;

//    public Bitmap getContactImage() { return contactImage; }
//
//    public void setContactImage(Bitmap contactImage) { this.contactImage = contactImage; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public Uri getPhotoURI() {
        return photoURI;
    }

    public void setPhotoURI(Uri photoURI) {
        this.photoURI = photoURI;
    }
}
