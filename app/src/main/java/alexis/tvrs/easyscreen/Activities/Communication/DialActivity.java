package alexis.tvrs.easyscreen.Activities.Communication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import alexis.tvrs.easyscreen.R;


public class DialActivity extends AppCompatActivity {
    private TextView phoneNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);
        phoneNumber = findViewById(R.id.activity_dial_num_display);
    }

    public void dial(View view) {
        Button button = (Button)view;
        String buttonText = button.getText().toString();
        String phoneNumberText = phoneNumber.getText().toString();
        String newNumber = phoneNumberText+buttonText;
        phoneNumber.setText(newNumber);
    }

    public void dialBack(View view) {
        String phoneNumberText = phoneNumber.getText().toString();
        String newNumber = phoneNumberText.substring(0, phoneNumberText.length() - 1);
        phoneNumber.setText(newNumber);
    }


    public void dialDelete(View view) {
        phoneNumber.setText("");
    }

    public void dialNumber(View view) {
        String number = phoneNumber.getText().toString();
        String callString = "tel:"+number;

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(callString));
        startActivity(intent);
    }
}
