package com.cheyrouse.gael.mynews.utils;

import android.app.Activity;
import android.content.Intent;
import com.cheyrouse.gael.mynews.controllers.activities.AboutActivity;
import com.cheyrouse.gael.mynews.controllers.activities.ArticleDetailActivity;
import com.cheyrouse.gael.mynews.controllers.activities.HelpActivity;
import com.cheyrouse.gael.mynews.controllers.activities.SearchActivity;
import static com.cheyrouse.gael.mynews.models.Result.TOPSTORIES_EXTRA;

public class IntentUtils {

    // Intent SearchActivity
    public static void launchIntentSearch(Activity actLauncher, Boolean b) {
        Intent intent = new Intent(actLauncher, SearchActivity.class);
        intent.putExtra("mode", b);
        actLauncher.startActivity(intent);
    }

    // Intent About and Help Activities
    public static void launchIntent(Activity actLauncher, Boolean val) {
        Intent intent;
        if (val) {
            intent = new Intent(actLauncher, AboutActivity.class);
        } else {
            intent = new Intent(actLauncher, HelpActivity.class);
        }
        actLauncher.startActivity(intent);
    }

    // Intent DetailActivity
    public static void startDetailActivity(Activity launcher, String url) {
        Intent detailActivityIntent = new Intent(launcher, ArticleDetailActivity.class);
        detailActivityIntent.putExtra(TOPSTORIES_EXTRA, url);
        launcher.startActivity(detailActivityIntent);
    }
}
