package alexis.tvrs.easyscreen.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Set;

public class InternetUrlPreferences {
    private final String FAVORITE_URL = "FAVORITE_URL";

    private Context context = null;
    public InternetUrlPreferences(Context context) { this.context = context; }

    public void saveArrayList(ArrayList<String> list){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(FAVORITE_URL, json);
        editor.apply();
    }

    public ArrayList<String> getArrayList(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(FAVORITE_URL, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
