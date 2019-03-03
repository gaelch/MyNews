package com.cheyrouse.gael.mynews.model;

import com.cheyrouse.gael.mynews.Models.Doc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DocTest {

    //Test Docs
    @Test
    public void createAndGetTest() throws Exception {
        Doc doc = new Doc();
        doc.setWebUrl("https");
        doc.setHeadline(null);
        doc.setSectionName("arts");
        doc.setKeywords(null);
        doc.setPubDate("pubDate");

        assertEquals("https", doc.getWebUrl());
        assertNull("", doc.getHeadline());
        assertEquals("pubDate", doc.getPubDate());
        assertEquals("arts", doc.getSectionName());
        assertNull("", doc.getKeywords());

    }
}
