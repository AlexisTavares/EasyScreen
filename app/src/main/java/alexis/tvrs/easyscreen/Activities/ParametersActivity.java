package alexis.tvrs.easyscreen.Activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import alexis.tvrs.easyscreen.R;
import alexis.tvrs.easyscreen.SharedPreferences.Preferences;

public class ParametersActivity extends AppCompatActivity {
    Preferences prefs = new Preferences(this);
    TextView textSizeDisplay;
    TextView textBoldDisplay;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameters);
        textBoldDisplay = findViewById(R.id.parameters_textStrongDisplay);
        textSizeDisplay = findViewById(R.id.parameters_textSizeDisplay);
    }

    public void setGlobalTextSize(View view) {
        TextView textView = (TextView) view;
        if(textView.getText().toString().equals(getResources().getString(R.string.textsize_option_large))){
            prefs.setFontSize("large");
            textSizeDisplay.setTextSize(22);
        }
        if(textView.getText().toString().equals(getResources().getString(R.string.textsize_option_medium))){
            prefs.setFontSize("medium");
            textSizeDisplay.setTextSize(18);
        }
        if(textView.getText().toString().equals(getResources().getString(R.string.textsize_option_small))){
            prefs.setFontSize("small");
            textSizeDisplay.setTextSize(14);
        }
    }

    public void setGlobalTextBold(View view) {
        TextView textView = (TextView) view;
        if(textView.getText().toString().equals(getResources().getString(R.string.textsize_option_bold))){
            prefs.setFontBold(true);
            textBoldDisplay.setTypeface(null, Typeface.BOLD);
        }
        if(textView.getText().toString().equals(getResources().getString(R.string.textsize_option_normal))){
            prefs.setFontBold(false);
            textBoldDisplay.setTypeface(null, Typeface.NORMAL);
        }
    }
}
