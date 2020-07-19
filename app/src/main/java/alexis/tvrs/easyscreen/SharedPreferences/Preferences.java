package alexis.tvrs.easyscreen.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Preferences {
    private final String FONT_STYLE = "FONT_STYLE";
    private final String FONT_BOLD = "FONT_BOLD";

    private Context context = null;
    public Preferences(Context context) { this.context = context; }

    protected SharedPreferences open() {
        return context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }

    protected  SharedPreferences.Editor edit() {
        return open().edit();
    }

    public String getFontSize() {
        return open().getString(FONT_STYLE,"medium");
    }

    public void setFontSize(String style) {
        edit().putString(FONT_STYLE, style).commit();
    }

    public void setFontBold(boolean bold){
        edit().putBoolean(FONT_BOLD, bold).commit();
    }

    public boolean getFontBold(){
        return open().getBoolean(FONT_BOLD,false);
    }

    public void setTextStyle(View view){
        if (view instanceof TextView) {
            switch (getFontSize()) {
                case "small":
                    ((TextView) view).setTextSize(14);
                    break;
                case "medium":
                    ((TextView) view).setTextSize(18);
                    break;
                case "large":
                    ((TextView) view).setTextSize(22);
                    break;
            }

            if(getFontBold()) ((TextView) view).setTypeface(null, Typeface.BOLD);
            else ((TextView) view).setTypeface(null, Typeface.NORMAL);
        }

        if (view instanceof EditText) {
            switch (getFontSize()) {
                case "small":
                    ((EditText) view).setTextSize(14);
                    break;
                case "medium":
                    ((EditText) view).setTextSize(18);
                    break;
                case "large":
                    ((EditText) view).setTextSize(22);
                    break;
            }

            if(getFontBold()) ((EditText) view).setTypeface(null, Typeface.BOLD);
            else ((EditText) view).setTypeface(null, Typeface.NORMAL);
        }

        if (view instanceof Button) {
            switch (getFontSize()) {
                case "small":
                    ((Button) view).setTextSize(12);
                    break;
                case "medium":
                    ((Button) view).setTextSize(16);
                    break;
                case "large":
                    ((Button) view).setTextSize(20);
                    break;
            }

            if(getFontBold()) ((Button) view).setTypeface(null, Typeface.BOLD);
            else ((Button) view).setTypeface(null, Typeface.NORMAL);
        }
    }
}