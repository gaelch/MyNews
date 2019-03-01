package com.cheyrouse.gael.mynews.utils;

public class Prefs {
    //This class saves moods using SharedPreferences and the Gson library

    /*    private static Prefs instance;
        private static final String Moods = "Mood";
        private static SharedPreferences prefs;


        //Class Prefs constructor
        private Prefs(Context context) {
            String PREFS_MOOD_STORE = "MoodStore";
            prefs = context.getSharedPreferences(PREFS_MOOD_STORE, Activity.MODE_PRIVATE);

        }
        //Prefs.get is called in SaveMoodHelper to create a new instance of Prefs
        public static Prefs get(Context context) {
            if (instance == null)
                instance = new Prefs(context);
            return instance;
        }

        //storeMoodStore change ArrayList into json strings and save it
        public void storeMoodStore(ArrayList<Mood> moodStore) {
            //start writing (open the file)
            SharedPreferences.Editor editor = prefs.edit();
            //put the data
            Gson gson = new Gson();
            String json = gson.toJson(moodStore);
            editor.putString(Moods, json);
            //close the file
            editor.apply();
        }

        //getMoodStore recovers json strings and return there in ArrayList
        public ArrayList<Mood> getMoodStore() {
            Gson gson = new Gson();
            String json = prefs.getString(Moods, "");

            ArrayList<Mood> moodStore;

            if (json.length() < 1) {
                moodStore = new ArrayList<>();
            } else {
                Type type = new TypeToken<ArrayList<Mood>>() {
                }.getType();
                moodStore = gson.fromJson(json, type);
            }

            //return the value that was stored under the key

            return moodStore;
        }*/

}
