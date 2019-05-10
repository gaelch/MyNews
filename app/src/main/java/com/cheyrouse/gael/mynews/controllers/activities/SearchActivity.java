package com.cheyrouse.gael.mynews.controllers.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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

import com.cheyrouse.gael.mynews.R;
import com.cheyrouse.gael.mynews.controllers.fragments.ResultToSearchFragment;
import com.cheyrouse.gael.mynews.models.Doc;
import com.cheyrouse.gael.mynews.models.SearchArticle;
import com.cheyrouse.gael.mynews.utils.IntentUtils;
import com.cheyrouse.gael.mynews.utils.NewYorkTimesStream;
import com.cheyrouse.gael.mynews.utils.Prefs;
import com.cheyrouse.gael.mynews.utils.DateUtils;
import com.cheyrouse.gael.mynews.utils.CheckUtils;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import static com.cheyrouse.gael.mynews.utils.NewYorkTimesService.API_KEY;
import static com.cheyrouse.gael.mynews.utils.DateUtils.API_DATE;
import static com.cheyrouse.gael.mynews.utils.DateUtils.TEXT_DATE;

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
    @BindView(R.id.view_search) View viewSearch;

    public static final String ARTICLES_SEARCH = "articles_search";
    private String keywords;
    private List<String> categories;
    private String beginDate;
    private String endDate;
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
        if(!mode){ //false = notifications
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
            switchNotif = prefs.getBoolean();
        }else{ // true = search
            switchNotification.setVisibility(View.GONE);
            viewSearch.setVisibility(View.GONE);
        }
    }

    //Configure editText
    private void configureEditTextSearch() {
        if(!mode) { //false = notifications
            if (prefs != null) {
                keywords = prefs.getKeywords();
            }
            editTextSearch.setText(keywords);
        }
            editTextSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) { }
                @Override
                public void afterTextChanged(Editable s) {
                    keywords = editTextSearch.getText().toString();
                }
            });
    }

    //Configure Begin date (calendar and datePickerDialog)
    private void configureBeginDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DatePickerDialogCustom,
                new DatePickerDialog.OnDateSetListener() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear+1;
                        textBeginDate.setText(DateUtils.getDate(year, dayOfMonth, monthOfYear, TEXT_DATE));
                        beginDate = DateUtils.getDate(year, dayOfMonth, monthOfYear, API_DATE);
                    }
                }, DateUtils.getYear(), DateUtils.getMonth(), DateUtils.getDay());
        datePickerDialog.show();
    }

    //Configure end date (calendar and datePickerDialog)
    private void configureEndDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DatePickerDialogCustom,
                new DatePickerDialog.OnDateSetListener() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear+1;
                        textEndDate.setText(DateUtils.getDate(year, dayOfMonth, monthOfYear, TEXT_DATE));
                        endDate = DateUtils.getDate(year, dayOfMonth, monthOfYear, API_DATE);
                    }
                }, DateUtils.getYear(), DateUtils.getMonth(), DateUtils.getDay());
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
        if(!mode) { //false = notifications
            categories = prefs.getCategories();
            if (prefs != null) {
                checkBoxArts.setChecked(CheckUtils.getCheckBoxBoolean(categories, "arts"));
                checkBoxPolitics.setChecked(CheckUtils.getCheckBoxBoolean(categories, "politics"));
                checkBoxBusiness.setChecked(CheckUtils.getCheckBoxBoolean(categories, "business"));
                checkBoxSciences.setChecked(CheckUtils.getCheckBoxBoolean(categories, "sciences"));
                checkBoxSport.setChecked(CheckUtils.getCheckBoxBoolean(categories, "sports"));
                checkBoxTravel.setChecked(CheckUtils.getCheckBoxBoolean(categories, "travel"));
            }
        }
    }

    // If switch is saved, check it
    private void configureSwitchNotification() {
        switchNotification.setChecked(CheckUtils.getSwitchPrefs(this));
        //To verify if keywords and checkBox are ok
        switchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!CheckUtils.checkToSaveNotifications(keywords, categories, getApplicationContext())){
                    switchNotification.setChecked(false);
                }
                if (switchNotification.isChecked()) {
                    switchNotif = true;
                    saveQueryParams();
                }else{
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
        switch (v.getId()){
            case R.id.buttonSearch:
                if(CheckUtils.toExecuteHttpRequest(keywords, categories, this)){
                    executeRequestWithSearchParams();
                }
                break;
            case R.id.checkBoxArts:
                if (checkBoxArts.isChecked()){
                    categories.add("arts");
                }
                else {
                    categories.remove("arts");
                }
                break;
            case R.id.checkBoxBusiness:
                if(checkBoxBusiness.isChecked()){
                    categories.add("business");
                }
                else{
                    categories.remove("business");
                }
                break;
            case R.id.checkBoxPolitics:
                if(checkBoxPolitics.isChecked()){
                    categories.add("politics");
                }
                else{
                    categories.remove("politics");
                }
                break;
            case R.id.checkBoxSciences:
                if(checkBoxSciences.isChecked()){
                    categories.add("sciences");
                }
                else{
                    categories.remove("sciences");
                }
                break;
            case R.id.checkBoxSports:
                if(checkBoxSport.isChecked()){
                    categories.add("sports");
                }
                else{
                    categories.remove("sports");
                }
                break;
            case R.id.checkBoxTravel:
                if(checkBoxTravel.isChecked()){
                    categories.add("travel");
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
        IntentUtils.startDetailActivity(this, searchArticle.getWebUrl());
    }

}
