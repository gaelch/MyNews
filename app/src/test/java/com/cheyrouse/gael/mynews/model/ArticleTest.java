package com.cheyrouse.gael.mynews.model;

import com.cheyrouse.gael.mynews.Models.Article;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArticleTest {

    //Test Article
    @Test
    public void articleGetTest() throws Exception{
        Article article = new Article();
        article.setStatus("ok");
        article.setCopyright("authorization");
        article.setNumResults(1);

        assertEquals("ok", article.getStatus());
        assertEquals("authorization", article.getCopyright());
        assertEquals(Long.valueOf(1), Long.valueOf(article.getNumResults()));
    }
}
