package com.cheyrouse.gael.mynews.controllers.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cheyrouse.gael.mynews.R;
import com.cheyrouse.gael.mynews.controllers.fragments.ResultToSearchFragment;
import com.cheyrouse.gael.mynews.models.Doc;
import com.cheyrouse.gael.mynews.models.SearchArticle;
import com.cheyrouse.gael.mynews.utils.NewYorkTimesStream;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static com.cheyrouse.gael.mynews.models.Result.TOPSTORIES_EXTRA;
import static com.cheyrouse.gael.mynews.utils.NewYorkTimesService.API_KEY;


public class SearchActivity extends AppCompatActivity implements View.OnClickListener, ResultToSearchFragment.ResultToSearchFragmentListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.editSearchBar) EditText editTextSearch;
    @BindView(R.id.editTextBeginDate) EditText editTextBeginDate;
    @BindView(R.id.editEndDate) EditText editTextEndDate;
    @BindView(R.id.checkBoxArts) CheckBox checkBoxArts;
    @BindView(R.id.checkBoxBusiness) CheckBox checkBoxBusiness;
    @BindView(R.id.checkBoxPolitics) CheckBox checkBoxPolitics;
    @BindView(R.id.checkBoxSciences) CheckBox checkBoxSciences;
    @BindView(R.id.checkBoxSports) CheckBox checkBoxSport;
    @BindView(R.id.checkBoxTravel) CheckBox checkBoxTravel;
    @BindView(R.id.buttonSearch)
    TextView buttonSearch;

    public static final String ARTICLES_SEARCH = "articles_search";
    private String keywords;
    private List<String> categories;
    private String beginDate;
    private String endDate;
    private int mYear;
    private int mMonth;
    private int mDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        configureToolbar();
        innitListener();
        hide_keyboard(this);
        configureEditTextSearch();
        categories = new ArrayList<>();
    }

    //To hide keyboard
    public static void hide_keyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if(view == null) {
            view = new View(activity);
        }
        Objects.requireNonNull(inputMethodManager).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    //Configure editText
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

    //Configure Begin date (calendar and datePickerDialog)
    private void configureBeginDate() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear+1;
                        String month = "";
                        if (monthOfYear >= 1 && monthOfYear <= 9){
                            month = String.valueOf("0" + monthOfYear);
                        }else{
                            month = String.valueOf(monthOfYear);
                        }
                        editTextBeginDate.setText(dayOfMonth + "-" + month + "-" + year);
                        beginDate = year + month + dayOfMonth;
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

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear+1;
                        String month = "";
                        if (monthOfYear >= 1 && monthOfYear <= 9){
                            month = String.valueOf("0" + monthOfYear);
                        }else{
                            month = String.valueOf(monthOfYear);
                        }
                        editTextEndDate.setText(dayOfMonth + "-" + month + "-" + year);
                        endDate = year + month + dayOfMonth;
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
        editTextBeginDate.setOnClickListener(this);
        editTextEndDate.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);
        checkBoxArts.setOnClickListener(this);
        checkBoxBusiness.setOnClickListener(this);
        checkBoxPolitics.setOnClickListener(this);
        checkBoxSciences.setOnClickListener(this);
        checkBoxSport.setOnClickListener(this);
        checkBoxTravel.setOnClickListener(this);
    }

    //OnClick to button Search and checkBox
    @Override
    public void onClick(View v) {
        String category;
        switch (v.getId()){
            case R.id.buttonSearch:
                if(keywords.isEmpty()){
                    Toast.makeText(this, "You must enter a keyword", Toast.LENGTH_LONG).show();
                }else {
                    if(categories.size() == 0){
                        Toast.makeText(this, "You must choose a category", Toast.LENGTH_LONG).show();
                    }else {
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
            case R.id.editTextBeginDate:
                configureBeginDate();
                break;
            case R.id.editEndDate:
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
