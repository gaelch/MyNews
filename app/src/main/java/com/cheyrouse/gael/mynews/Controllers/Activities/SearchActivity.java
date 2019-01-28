package com.cheyrouse.gael.mynews.Controllers.Activities;

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

import com.cheyrouse.gael.mynews.Controllers.Fragments.ResultToSearchFragment;
import com.cheyrouse.gael.mynews.Models.Doc;
import com.cheyrouse.gael.mynews.Models.SearchArticle;
import com.cheyrouse.gael.mynews.R;
import com.cheyrouse.gael.mynews.Utils.NewYorkTimesStream;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static com.cheyrouse.gael.mynews.Models.Result.TOPSTORIES_EXTRA;
import static com.cheyrouse.gael.mynews.Utils.NewYorkTimesService.API_KEY;


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
        final Calendar myCalendar = Calendar.getInstance();
        private DatePickerDialog datePickerDialog;

        private Disposable disposable;
        private String keywords = "android";
        private String category = "world";
        private List<String> categories;
        private String beginDate = "20161220";
        private String endDate =  "20171220";
        private int mYear, mMonth, mDay, mHour;


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


    private void configureBeginDate() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {

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

    private void configureEndDate() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {

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

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.buttonSearch:
                    executeRequestWithSearchParams();
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
                case R.id.editTextBeginDate:
                    configureBeginDate();
                    break;
                case R.id.editEndDate:
                    configureEndDate();
                    break;
            }

        }

        private void executeRequestWithSearchParams(){
            Log.e("test", String.valueOf(categories));
            Log.e("test", keywords);
            this.disposable = NewYorkTimesStream.streamFetchArticleSearch(API_KEY, keywords, categories, beginDate, endDate).subscribeWith(new DisposableObserver<SearchArticle>() {
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

    @Override
    public void callbackSearchArticle(Doc searchArticle) {
        startDetailActivityToSearch(searchArticle);
    }

    private void startDetailActivityToSearch(Doc searchArticle) {
        Intent detailActivityIntent = new Intent(SearchActivity.this, ArticleDetailActivity.class);
        detailActivityIntent.putExtra(TOPSTORIES_EXTRA, searchArticle.getWebUrl());
        startActivity(detailActivityIntent);
    }

}
