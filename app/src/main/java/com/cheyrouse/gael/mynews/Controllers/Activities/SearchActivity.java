package com.cheyrouse.gael.mynews.Controllers.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.cheyrouse.gael.mynews.Controllers.Fragments.ResultToSearchFragment;
import com.cheyrouse.gael.mynews.Models.SearchArticle;
import com.cheyrouse.gael.mynews.R;
import com.cheyrouse.gael.mynews.Utils.NewYorkTimesStream;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static com.cheyrouse.gael.mynews.Utils.NewYorkTimesService.API_KEY;


public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

        @BindView(R.id.toolbar) Toolbar toolbar;
        @BindView(R.id.editSearchBar) SearchView searchView;
        @BindView(R.id.result_to_search_date_picker_begin) DatePicker datePickerBeginDate;
        @BindView(R.id.result_to_search_date_picker_end) DatePicker datePickerEndDate;
        @BindView(R.id.checkBoxArts) CheckBox checkBoxArts;
        @BindView(R.id.checkBoxBusiness) CheckBox checkBoxBusiness;
        @BindView(R.id.checkBoxPolitics) CheckBox checkBoxPolitics;
        @BindView(R.id.checkBoxSciences) CheckBox checkBoxSciences;
        @BindView(R.id.checkBoxSports) CheckBox checkBoxSport;
        @BindView(R.id.checkBoxTravel) CheckBox checkBoxTravel;
        @BindView(R.id.buttonSearch) TextView buttonSearch;

        public static final String ARTICLES_SEARCH = "articles_search";

        private Disposable disposable;
        //private List<Keyword> keywords;
        private String keywords = "android";
        private String category = "world";
        private String beginDate = "20161220";
        private String endDate =  "20171220";

        @SuppressLint("RestrictedApi")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search);
            ButterKnife.bind(this);
            configureToolbar();
            innitListener();
            configureEditTextSearch();
            configureBeginAndEndDate();
        }

        private void configureBeginAndEndDate() {
            Calendar cal = Calendar.getInstance();

            datePickerBeginDate.init( cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener(){
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    monthOfYear = monthOfYear+1;
                    String month = "";
                    if (monthOfYear >= 1 && monthOfYear <= 9){
                        month = String.valueOf("0" + monthOfYear);
                    }else{
                        month = String.valueOf(monthOfYear);
                    }
                    if(year < getCurrentYear()){
                        beginDate = String.valueOf(year +"-"+ month +"-"+ dayOfMonth);
                        //buttonNextDate.setEnabled(true);
                        //buttonNextDate.setBackgroundColor(buttonNextDate.getContext().getResources().getColor(R.color.colorButton));
                    }
                }
            });

            datePickerEndDate.init( cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener(){
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    monthOfYear = monthOfYear+1;
                    String month = "";
                    if (monthOfYear >= 1 && monthOfYear <= 9){
                        month = String.valueOf("0" + monthOfYear);
                    }else{
                        month = String.valueOf(monthOfYear);
                    }
                    if(year < getCurrentYear()){
                        endDate = String.valueOf(year +"-"+ month +"-"+ dayOfMonth);
                        //buttonNextDate.setEnabled(true);
                        //buttonNextDate.setBackgroundColor(buttonNextDate.getContext().getResources().getColor(R.color.colorButton));
                    }
                }
            });
        }

    public int getCurrentYear(){
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

        private void configureEditTextSearch() {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    //keywords = (searchView.getQuery());
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return true;
                }
            });

              //keywords.subList(keywords);
        }


        public void configureToolbar(){
            // Set the Toolbar
            setSupportActionBar(toolbar);
            // Get a support ActionBar corresponding to this toolbar
          /*  ActionBar ab = getSupportActionBar();
            // Enable the Up button
            ab.setDisplayHomeAsUpEnabled(true);*/
        }

        private void innitListener(){
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
                        Toast.makeText(this, "checkBoxArts is checked !",Toast.LENGTH_SHORT).show();}
                    break;
                case R.id.checkBoxBusiness:
                    if(checkBoxBusiness.isChecked()){
                        category = "business";
                        Toast.makeText(this, "checkBoxBusiness is checked !",Toast.LENGTH_SHORT).show();}
                    break;
                case R.id.checkBoxPolitics:
                    if(checkBoxPolitics.isChecked()){
                        category = "politics";
                        Toast.makeText(this, "checkBoxPolitics is checked !",Toast.LENGTH_SHORT).show();}
                    break;
                case R.id.checkBoxSciences:
                    if(checkBoxSciences.isChecked()){
                        category = "sciences";
                        Toast.makeText(this, "checkBoxSciences is checked !",Toast.LENGTH_SHORT).show();}
                    break;
                case R.id.checkBoxSports:
                    if(checkBoxSport.isChecked()){
                        category = "sports";
                        Toast.makeText(this, "checkBoxSport is checked !",Toast.LENGTH_SHORT).show();}
                    break;
                case R.id.checkBoxTravel:
                    if(checkBoxTravel.isChecked()){
                        category = "travel";
                        Toast.makeText(this, "checkBoxTravel is checked !",Toast.LENGTH_SHORT).show();}
                    break;
            }

        }


        private void executeRequestWithSearchParams(){
            this.disposable = NewYorkTimesStream.streamFetchArticleSearch(API_KEY, keywords, category, beginDate, endDate).subscribeWith(new DisposableObserver<SearchArticle>() {
                @Override
                public void onNext(SearchArticle articles) {
                    configureSearchFragment(articles);
                }

                @Override
                public void onError(Throwable e) {
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

}
