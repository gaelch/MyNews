package com.cheyrouse.gael.mynews.utils;
import com.cheyrouse.gael.mynews.models.SearchArticle;

public class NotificationUtils {

    public static final String TEXTCONTENT = "Nous n'avons trouvé aucun nouvel article pour vous aujourd'hui";

    public static String getTextContent(SearchArticle articles){
        String textContent;
        if (articles == null){
           textContent = TEXTCONTENT;
        }else{
            textContent = "MyNews founds " + articles.getResponse().getDocs().size() + " articles today";
            if(articles.getResponse().getDocs().size() == 0){
                textContent = TEXTCONTENT;
            }
        }
        return textContent;
    }
}
