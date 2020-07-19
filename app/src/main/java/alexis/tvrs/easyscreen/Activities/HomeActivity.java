package alexis.tvrs.easyscreen.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import alexis.tvrs.easyscreen.Activities.Communication.CommunicationActivity;
import alexis.tvrs.easyscreen.Activities.Contact.ContactGridActivity;
import alexis.tvrs.easyscreen.Activities.Gallery.GalleryActivity;
import alexis.tvrs.easyscreen.Activities.Internet.InternetActivity;
import alexis.tvrs.easyscreen.Activities.Communication.Message.MessageReadActivity;
import alexis.tvrs.easyscreen.R;

public class HomeActivity extends AppCompatActivity {
    public static String ORIGIN = "ORIGIN_HOME_ACTIVITY";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void loadGallery(View view) {
        Intent intent = new Intent(HomeActivity.this, GalleryActivity.class);
        startActivity(intent);
    }

    public void loadCommunication(View view) {
        Intent intent = new Intent(HomeActivity.this, CommunicationActivity.class);
        startActivity(intent);
    }

    public void loadInternet(View view) {
        Intent intent = new Intent(HomeActivity.this, InternetActivity.class);
        startActivity(intent);
    }

    public void loadContact(View view) {
        Intent intent = new Intent(HomeActivity.this, ContactGridActivity.class);
        intent.putExtra("ORIGIN",ORIGIN);
        startActivity(intent);
    }

    public void loadMessage(View view) {
        Intent intent = new Intent(HomeActivity.this, MessageReadActivity.class);
        startActivity(intent);
    }

    public void loadAgenda(View view) {
        Intent intent = new Intent(HomeActivity.this, AgendaActivity.class);
        startActivity(intent);
    }

    public void loadParameters(View view){
        Intent intent = new Intent(HomeActivity.this, ParametersActivity.class);
        startActivity(intent);
    }
}
