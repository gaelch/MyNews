package com.cheyrouse.gael.mynews.controllers.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.cheyrouse.gael.mynews.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

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
    private Boolean switchNotif;
    public static final String MY_PREFS = "my_prefs";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        categories = new ArrayList<>();
        configureToolbar();
        innitListener();
        configureSharedPreferences();
        configureSwitchNotification();
        configureEditTextSearch();
        configureCheckBoxPrefs();
    }

    //If Prefs is not null check saved CheckBox
    private void configureCheckBoxPrefs() {
        if(sharedPreferences != null){
            Gson gson = new Gson();
            String json = sharedPreferences.getString("categories", null);
            categories = gson.fromJson(json, new TypeToken<ArrayList<String>>(){}.getType());

            if(switchNotif){
                if(categories != null && categories.contains("arts")){
                    checkBoxArts.setChecked(true);
                }
                if(categories != null && categories.contains("business")){
                    checkBoxBusiness.setChecked(true);
                }
                if(categories != null && categories.contains("politics")){
                    checkBoxPolitics.setChecked(true);
                }
                if(categories != null && categories.contains("sports")){
                    checkBoxSport.setChecked(true);
                }
                if(categories != null && categories.contains("sciences")){
                    checkBoxSciences.setChecked(true);
                }
                if(categories != null && categories.contains("travel")){
                    checkBoxTravel.setChecked(true);
                }
            }
        }
    }

    // If switch is saved, check it
    private void configureSwitchNotification() {
        if(sharedPreferences != null) {
            switchNotif = sharedPreferences.getBoolean("switch", false);
            if (switchNotif) {
                switchNotification.setChecked(true);
            }
        }
        //To verify if keywords and checkBox are ok
        switchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(keywords.isEmpty()) {
                    Toast.makeText(NotificationActivity.this, "You must enter a keyword", Toast.LENGTH_LONG).show();
                    switchNotification.setChecked(false);
                }
                if (categories.size() == 0) {
                    Toast.makeText(NotificationActivity.this, "You must choose a category", Toast.LENGTH_LONG).show();
                    switchNotification.setChecked(false);
                }

                if (switchNotification.isChecked()) {
                    switchNotif = true;
                    saveQueryParams();
                }
                if (!switchNotification.isChecked()) {
                    switchNotif = false;
                    saveQueryParams();
                }
            }



        });

    }

    //Request prefs
    private void configureSharedPreferences() {
        sharedPreferences = getApplicationContext().getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        Log.e("test", "ok");
    }

    //Set the toolbar
    public void configureToolbar(){
        // Set the Toolbar
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
    }

    //Initialisation of listeners
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

    //Set saved words on EditText and get words
    private void configureEditTextSearch() {
        if(sharedPreferences != null){
            keywords = sharedPreferences.getString("keywords", "");
        }
        if(switchNotif){
            editTextSearch.setText(keywords);
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

    }

    // To check checkBox and add on a list
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.checkBoxArts:
                if (checkBoxArts.isChecked()){
                    category = "arts";
                    categories.add(category);
                }
                else {
                    categories.remove("arts");
                }
                break;
            case R.id.checkBoxBusiness:
                if(checkBoxBusiness.isChecked()){
                    category = "business";
                    categories.add(category);
                }
                else{
                    categories.remove("business");
                }
                break;
            case R.id.checkBoxPolitics:
                if(checkBoxPolitics.isChecked()){
                    category = "politics";
                    categories.add(category);
                }
                else{
                    categories.remove("politics");
                }
                break;
            case R.id.checkBoxSciences:
                if(checkBoxSciences.isChecked()){
                    category = "sciences";
                    categories.add(category);
                }
                else{
                    categories.remove("sciences");
                }
                break;
            case R.id.checkBoxSports:
                if(checkBoxSport.isChecked()){
                    category = "sports";
                    categories.add(category);
                }
                else{
                    categories.remove("sports");
                }
                break;
            case R.id.checkBoxTravel:
                if(checkBoxTravel.isChecked()){
                    category = "travel";
                    categories.add(category);
                }
                else{
                    categories.remove("travel");
                }
                break;
        }
    }

    //Save params
    private void saveQueryParams() {
        sharedPreferences = getApplicationContext().getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("keywords", keywords);
        editor.putBoolean("switch", switchNotif);
        Gson gson = new Gson();
        String json = gson.toJson(categories);
        editor.putString("categories", json);
        editor.apply();
    }
}
