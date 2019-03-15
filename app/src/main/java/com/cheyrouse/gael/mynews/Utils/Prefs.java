package com.cheyrouse.gael.mynews.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.cheyrouse.gael.mynews.Controllers.activities.NotificationActivity.MY_PREFS;

public class Prefs {
  //This class using SharedPreferences and the Gson library
    private static Prefs instance;
        private static SharedPreferences prefs;


        //Class Prefs constructor
       Prefs(Context context) {
            prefs = context.getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        }
        //Prefs.get is called in SaveMoodHelper to create a new instance of Prefs
        public static Prefs get(Context context) {
            if (instance == null)
                instance = new Prefs(context);
            return instance;
        }

        Prefs(){
        }

        public static Prefs PrefsInit(SharedPreferences sharedPreferences){
            instance = new Prefs();
            prefs = sharedPreferences;
            return instance;
        }

        //storeMoodStore change ArrayList into json strings and save it
        public void storeCategories(List<String> categories) {

            //start writing (open the file)
            SharedPreferences.Editor editor = prefs.edit();
            //put the data
            Gson gson = new Gson();
            String json = gson.toJson(categories);
            editor.putString("categories", json);
            //close the file
            editor.apply();
        }

        //getMoodStore recovers json strings and return there in ArrayList
        public ArrayList<String> getCategories() {
            Gson gson = new Gson();
            String json = prefs.getString("categories", "");

            ArrayList<String> categories;

            if (json.length() < 1) {
               categories = new ArrayList<>();
            } else {
                Type type = new TypeToken<ArrayList<String>>() {
                }.getType();
                categories = gson.fromJson(json, type);
            }

            //return the value that was stored under the key

            return categories;
        }

        public void storeKeywords(String keywords){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("keywords", keywords);
            editor.apply();
        }

        public String getKeywords(){
            return prefs.getString("keywords", "");
        }

        public void storeBoolean(Boolean aSwitch){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("switch", aSwitch);
            editor.apply();
        }

        public Boolean getBoolean(){
            return prefs.getBoolean("switch", false);
        }

}
