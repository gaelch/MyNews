package com.cheyrouse.gael.mynews;

import com.cheyrouse.gael.mynews.models.SearchArticle;
import com.cheyrouse.gael.mynews.utils.NotificationUtils;
import org.junit.Test;

import static com.cheyrouse.gael.mynews.utils.NotificationUtils.TEXTCONTENT;
import static org.junit.Assert.assertEquals;


public class AlarmReceiverTest {
    private static final SearchArticle searchArticle = null;

    //test NotificationUtils If articles are null
    @Test
    public void testTextNotificationContent(){
        assertEquals(TEXTCONTENT, NotificationUtils.getTextContent(searchArticle));
    }
}
