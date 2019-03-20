package com.cheyrouse.gael.mynews.Controllers.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.cheyrouse.gael.mynews.R;
import com.cheyrouse.gael.mynews.Utils.Prefs;

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

    private List<String> categories;
    private String keywords;
    private Boolean switchNotif;
    public static final String MY_PREFS = "my_prefs";
    private Prefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        prefs = Prefs.get(NotificationActivity.this);
        categories = new ArrayList<>();
        configureToolbar();
        innitListener();
        configureSwitchNotification();
        configureEditTextSearch();
        configureCheckBoxPrefs();
    }

    //If Prefs is not null check saved CheckBox
    private void configureCheckBoxPrefs() {
        if(prefs != null){
            categories = prefs.getCategories();

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

    // If switch is saved, check it
    private void configureSwitchNotification() {
        if(prefs != null) {
            switchNotif = prefs.getBoolean();
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
        if(prefs != null){
            keywords = prefs.getKeywords();
        }
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

    // To check checkBox and add on a list
    @Override
    public void onClick(View v) {

        String category;
        switch (v.getId()) {
            case R.id.checkBoxArts:
                if (checkBoxArts.isChecked()) {
                    category = "arts";
                    categories.add(category);
                } else {
                    categories.remove("arts");
                }
                break;
            case R.id.checkBoxBusiness:
                if (checkBoxBusiness.isChecked()) {
                    category = "business";
                    categories.add(category);
                } else {
                    categories.remove("business");
                }
                break;
            case R.id.checkBoxPolitics:
                if (checkBoxPolitics.isChecked()) {
                    category = "politics";
                    categories.add(category);
                } else {
                    categories.remove("politics");
                }
                break;
            case R.id.checkBoxSciences:
                if (checkBoxSciences.isChecked()) {
                    category = "sciences";
                    categories.add(category);
                } else {
                    categories.remove("sciences");
                }
                break;
            case R.id.checkBoxSports:
                if (checkBoxSport.isChecked()) {
                    category = "sports";
                    categories.add(category);
                } else {
                    categories.remove("sports");
                }
                break;
            case R.id.checkBoxTravel:
                if (checkBoxTravel.isChecked()) {
                    category = "travel";
                    categories.add(category);
                } else {
                    categories.remove("travel");
                }
                break;
        }
    }

    //call onPause to save the settings if the switch is not touched
    protected void onPause() {
        super.onPause();
        saveQueryParams();
    }

    //Save params
    private void saveQueryParams() {
        prefs.storeCategories(categories);
        prefs.storeKeywords(keywords);
        prefs.storeBoolean(switchNotif);
    }
}
