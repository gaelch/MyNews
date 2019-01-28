package com.cheyrouse.gael.mynews;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.cheyrouse.gael.mynews.Utils.AlarmReceiver;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.checkBoxArts) CheckBox checkBoxArts;
    @BindView(R.id.checkBoxBusiness) CheckBox checkBoxBusiness;
    @BindView(R.id.checkBoxPolitics) CheckBox checkBoxPolitics;
    @BindView(R.id.checkBoxSciences) CheckBox checkBoxSciences;
    @BindView(R.id.checkBoxSports) CheckBox checkBoxSport;
    @BindView(R.id.checkBoxTravel) CheckBox checkBoxTravel;
    @BindView(R.id.editSearchBar) EditText editTextSearch;
    @BindView(R.id.switch_notification) Switch switchNotification;

    private String category;
    private List<String> categories;
    private String keywords;
    private SharedPreferences sharedPreferences;
    public static final String MY_PREFS = "my_prefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        categories = new ArrayList<>();
        configureToolbar();
        configureSharedPreferences();
        innitListener();
        configureEditTextSearch();
        SaveQueryParams();
        configureSwitchNotification();
    }

    private void configureSwitchNotification() {
        if(switchNotification.isActivated()){
            configureAlarmNotification(this);
        }
    }

    private void configureSharedPreferences() {
        sharedPreferences = getApplicationContext().getSharedPreferences(MY_PREFS, MODE_PRIVATE);
    }

    public void configureToolbar(){
        // Set the Toolbar
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void innitListener(){
        editTextSearch.setOnClickListener(this);
        switchNotification.setOnClickListener(this);
        checkBoxArts.setOnClickListener(this);
        checkBoxBusiness.setOnClickListener(this);
        checkBoxPolitics.setOnClickListener(this);
        checkBoxSciences.setOnClickListener(this);
        checkBoxSport.setOnClickListener(this);
        checkBoxTravel.setOnClickListener(this);
    }

    private void configureEditTextSearch() {
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                keywords = editTextSearch.getText().toString();
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.switch_notification:
                break;
            case R.id.checkBoxArts:
                if (checkBoxArts.isChecked()){
                    category = "arts";
                    categories.add(category);
                    Toast.makeText(this, "checkBoxArts is checked !",Toast.LENGTH_SHORT).show();}
                else {
                    categories.remove("arts");
                }
                break;
            case R.id.checkBoxBusiness:
                if(checkBoxBusiness.isChecked()){
                    category = "business";
                    categories.add(category);
                    Toast.makeText(this, "checkBoxBusiness is checked !",Toast.LENGTH_SHORT).show();}
                else{
                    categories.remove("business");
                }
                break;
            case R.id.checkBoxPolitics:
                if(checkBoxPolitics.isChecked()){
                    category = "politics";
                    categories.add(category);
                    Toast.makeText(this, "checkBoxPolitics is checked !",Toast.LENGTH_SHORT).show();}
                else{
                    categories.remove("politics");
                }
                break;
            case R.id.checkBoxSciences:
                if(checkBoxSciences.isChecked()){
                    category = "sciences";
                    categories.add(category);
                    Toast.makeText(this, "checkBoxSciences is checked !",Toast.LENGTH_SHORT).show();}
                else{
                    categories.remove("sciences");
                }
                break;
            case R.id.checkBoxSports:
                if(checkBoxSport.isChecked()){
                    category = "sports";
                    categories.add(category);
                    Toast.makeText(this, "checkBoxSport is checked !",Toast.LENGTH_SHORT).show();}
                else{
                    categories.remove("sports");
                }
                break;
            case R.id.checkBoxTravel:
                if(checkBoxTravel.isChecked()){
                    category = "travel";
                    categories.add(category);
                    Toast.makeText(this, "checkBoxTravel is checked !",Toast.LENGTH_SHORT).show();}
                else{
                    categories.remove("travel");
                }
                break;
        }
    }

    private void SaveQueryParams() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MY_PREFS, keywords);
        Gson gson = new Gson();
        String json = gson.toJson(categories);
        editor.putString(MY_PREFS, json);
        editor.apply();
        if (switchNotification.isChecked()) {
            editor.putBoolean("SWITCH", true);
            editor.apply();
        } else {
            editor.putBoolean("SWITCH", false);
            editor.apply();
        }
    }

    //At midnight, the alarm goes off to save the mood of the day
    private void configureAlarmNotification(Context context) {
        AlarmManager alarmManager;
        PendingIntent pendingIntent;

        //in a current date at midnight, this property get an instance to calendar
        Calendar calendar = Calendar.getInstance(Locale.FRANCE);
        calendar.set(Calendar.HOUR_OF_DAY, 17);
        calendar.set(Calendar.MINUTE, 5);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE, 1);

        boolean switchNotif = sharedPreferences.getBoolean("SWITCH", false);
        //call AlarmReceiver class
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if(switchNotif){
            Intent intent;
            intent = new Intent(context, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            if (alarmManager != null) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }

        //RTC-WAKEUP that will wake the device when it turns off.
        if (alarmManager != null && switchNotif) {
            Intent intent;
            intent = new Intent(context, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

}
