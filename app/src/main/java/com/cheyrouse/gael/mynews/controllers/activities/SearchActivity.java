package com.cheyrouse.gael.mynews.controllers.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.cheyrouse.gael.mynews.R;
import com.cheyrouse.gael.mynews.controllers.fragments.ResultToSearchFragment;
import com.cheyrouse.gael.mynews.models.Doc;
import com.cheyrouse.gael.mynews.models.SearchArticle;
import com.cheyrouse.gael.mynews.utils.NewYorkTimesStream;
import com.cheyrouse.gael.mynews.utils.Prefs;
import com.cheyrouse.gael.mynews.utils.StringDateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static com.cheyrouse.gael.mynews.models.Result.TOPSTORIES_EXTRA;
import static com.cheyrouse.gael.mynews.utils.NewYorkTimesService.API_KEY;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, ResultToSearchFragment.ResultToSearchFragmentListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.editSearchBar) EditText editTextSearch;
    @BindView(R.id.textBeginDate) TextView textBeginDate;
    @BindView(R.id.textEndDate) TextView textEndDate;
    @BindView(R.id.checkBoxArts) CheckBox checkBoxArts;
    @BindView(R.id.checkBoxBusiness) CheckBox checkBoxBusiness;
    @BindView(R.id.checkBoxPolitics) CheckBox checkBoxPolitics;
    @BindView(R.id.checkBoxSciences) CheckBox checkBoxSciences;
    @BindView(R.id.checkBoxSports) CheckBox checkBoxSport;
    @BindView(R.id.checkBoxTravel)CheckBox checkBoxTravel;
    @BindView(R.id.buttonSearch) TextView buttonSearch;
    @BindView(R.id.switch_notification) Switch switchNotification;
    @BindView(R.id.textViewBegin) TextView tvBegin;
    @BindView(R.id.textViewEnd) TextView tvEnd;
    @BindView(R.id.lineEnd) View viewEnd;
    @BindView(R.id.line1) View viewStart;

    public static final String ARTICLES_SEARCH = "articles_search";
    private String keywords;
    private List<String> categories;
    private String beginDate;
    private String endDate;
    private int mYear;
    private int mMonth;
    private int mDay;
    private boolean mode;
    private Prefs prefs;
    private Boolean switchNotif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        configureToolbar();
        categories = new ArrayList<>();
        innitListener();
        getIntentMode();
        configureEditTextSearch();

    }

    private void getIntentMode() {
        mode = getIntent().getBooleanExtra("mode", true);
        if(!mode){
            prefs = Prefs.get(SearchActivity.this);
            switchNotification.setVisibility(View.VISIBLE);
            tvBegin.setVisibility(View.GONE);
            tvEnd.setVisibility(View.GONE);
            viewStart.setVisibility(View.GONE);
            viewEnd.setVisibility(View.GONE);
            buttonSearch.setVisibility(View.GONE);
            textBeginDate.setVisibility(View.GONE);
            textEndDate.setVisibility(View.GONE);
            configureCheckBoxPrefs();
            configureSwitchNotification();
        }else{
            switchNotification.setVisibility(View.GONE);
        }
    }

    //Configure editText
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

    //Configure Begin date (calendar and datePickerDialog)
    private void configureBeginDate() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DatePickerDialogCustom,
                new DatePickerDialog.OnDateSetListener() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear+1;
                        textBeginDate.setText(StringDateUtils.getDate(year, dayOfMonth, monthOfYear));
                        beginDate = StringDateUtils.getDateForSearch(year, dayOfMonth, monthOfYear);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    //Configure end date (calendar and datePickerDialog)
    private void configureEndDate() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DatePickerDialogCustom,
                new DatePickerDialog.OnDateSetListener() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear+1;
                        textEndDate.setText(StringDateUtils.getDate(year, dayOfMonth, monthOfYear));
                        endDate = StringDateUtils.getDateForSearch(year, dayOfMonth, monthOfYear);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
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

    //Init Listeners
    private void innitListener(){
        editTextSearch.setOnClickListener(this);
        textBeginDate.setOnClickListener(this);
        textEndDate.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);
        checkBoxArts.setOnClickListener(this);
        checkBoxBusiness.setOnClickListener(this);
        checkBoxPolitics.setOnClickListener(this);
        checkBoxSciences.setOnClickListener(this);
        checkBoxSport.setOnClickListener(this);
        checkBoxTravel.setOnClickListener(this);
        switchNotification.setOnClickListener(this);
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
                    Toast.makeText(SearchActivity.this, "You must enter a keyword", Toast.LENGTH_LONG).show();
                    switchNotification.setChecked(false);
                }
                if (categories.size() == 0) {
                    Toast.makeText(SearchActivity.this, "You must choose a category", Toast.LENGTH_LONG).show();
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

    //call onPause to save the settings if the switch is not touched
    protected void onPause() {
        super.onPause();
        if(!mode){
            saveQueryParams();
        }
    }

    //Save params
    private void saveQueryParams() {
        prefs.storeCategories(categories);
        prefs.storeKeywords(keywords);
        prefs.storeBoolean(switchNotif);
    }

    //OnClick to button Search and checkBox
    @Override
    public void onClick(View v) {
        String category;
        switch (v.getId()){
            case R.id.buttonSearch:
                if (keywords == null) {
                    Toast.makeText(this, "You must enter a keyword", Toast.LENGTH_LONG).show();
                } else if (keywords.isEmpty()) {
                    Toast.makeText(this, "You must enter a keyword", Toast.LENGTH_LONG).show();
                } else {
                    if (categories.size() == 0) {
                        Toast.makeText(this, "You must choose a category", Toast.LENGTH_LONG).show();
                    } else {
                        executeRequestWithSearchParams();
                    }
                }
                break;

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
            case R.id.textBeginDate:
                configureBeginDate();
                break;
            case R.id.textEndDate:
                configureEndDate();
                break;
        }

    }

    //Request Articles with user search params
    private void executeRequestWithSearchParams(){
        Log.e("test", String.valueOf(categories));
        Log.e("test", keywords);
        Disposable disposable = NewYorkTimesStream.streamFetchArticleSearch(API_KEY, keywords, categories, beginDate, endDate).subscribeWith(new DisposableObserver<SearchArticle>() {
            @Override
            public void onNext(SearchArticle articles) {
                configureSearchFragment(articles);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("test", e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e("Test", "Search is charged");
            }
        });
    }

    //To launch SearchFragment
    private void configureSearchFragment(SearchArticle articles){
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARTICLES_SEARCH, articles);
        ResultToSearchFragment resultToSearchFragment = (ResultToSearchFragment) getSupportFragmentManager().findFragmentById(R.id.activity_search_frame_layout);
        if (resultToSearchFragment == null) {
            resultToSearchFragment = new ResultToSearchFragment();
            resultToSearchFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_search_frame_layout, resultToSearchFragment)
                    .commit();
        }
    }

    //CallBack to Search to show DetailActivity
    @Override
    public void callbackSearchArticle(Doc searchArticle) {
        startDetailActivityToSearch(searchArticle);
    }

    //Start DetailActivity
    private void startDetailActivityToSearch(Doc searchArticle) {
        Intent detailActivityIntent = new Intent(SearchActivity.this, ArticleDetailActivity.class);
        detailActivityIntent.putExtra(TOPSTORIES_EXTRA, searchArticle.getWebUrl());
        startActivity(detailActivityIntent);
    }
}
