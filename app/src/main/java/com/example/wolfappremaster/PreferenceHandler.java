package com.example.wolfappremaster;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class PreferenceHandler {

    public static final String SETTING_PREFERENCE = "setting_preference";

    private final Context context;

    private HashMap<String,SharedPreferences> preferences;
    private Gson gson;

    public PreferenceHandler(Context context) {
        this.context = context;
        preferences = new HashMap<>();
        SharedPreferences setting = context.getSharedPreferences(SETTING_PREFERENCE,0);
        preferences.put(SETTING_PREFERENCE,setting);
        gson = new GsonBuilder().create();
        for(String i : setting.getString("preferences","").split(",")) {
            if (!i.isEmpty()) preferences.put(i,context.getSharedPreferences(i,0));
        }
        Log.d("string",preferences.toString());
    }

    public void addPreference(String name) {
        if (containsPreference(name)) return;
        preferences.put(name,context.getSharedPreferences(name,0));
        String preferences = getString(SETTING_PREFERENCE,"preferences");
        if (preferences == null) preferences = name;
        else preferences += "," + name;
        setString(SETTING_PREFERENCE,"preferences",preferences);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void removePreference(String name) {
        if (!containsPreference(name)) return;
        SharedPreferences.Editor editor = preferences.get(name).edit();
        editor.clear();
        editor.commit();
        preferences.remove(name);
        Log.d("string",preferences.toString());
        setString(SETTING_PREFERENCE,"preferences",
                preferences.keySet().stream().filter(s -> !s.equals(SETTING_PREFERENCE)).collect(Collectors.joining(",")));
    }


    public String getString(String name,String key) {
        SharedPreferences pref = preferences.get(name);
        if (pref == null) return null;
        return pref.getString(key,null);
    }

    public void setString(String name,String key,String value) {
        SharedPreferences pref = preferences.get(name);
        if (pref == null) return;
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public Speech getSpeech(String name, String key) {
        String text = getString(name,key);
        if (text == null) return null;
        return gson.fromJson(text,Speech.class);
    }

    public void setSpeech(String name, String key, Speech speech) {
        if (speech != null) setString(name,key,gson.toJson(speech));
    }

//    public CharacterItem getCharacter(String name,String key) {
//        String text = getString(name,key);
//        if (text == null) return null;
//        return gson.fromJson(text,CharacterItem.class);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public void setCharacter(String name, String key, CharacterItem item) {
//        HashMap<String,Speech> map = new HashMap<>();
//        item.getSpeeches().values().stream().filter(speech -> !speech.equals(item.getCharacter().getSpeeches().get(speech.getOrder())))
//                .forEach(speech -> map.put(speech.getOrder(),speech));
//        item.setSpeeches(map);
//        setString(name,key,gson.toJson(item));
//    }

    public boolean containsCharacter(String name, String key) {
        return preferences.get(name).contains(key);
    }

    public boolean containsPreference(String name) {
        return preferences.containsKey(name);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<String> getPreferences() {
        return preferences.keySet().stream().collect(Collectors.toList());
    }


}
