package au.edu.rmit.sept.webapp.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import au.edu.rmit.sept.webapp.models.Article;
import au.edu.rmit.sept.webapp.services.ArticleService;

@SpringBootTest
@AutoConfigureMockMvc
public class ArticleTests {

    private static final String URL = "/articles";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ArticleService service;

    @Test
    void shouldDisplayArticlesPage() throws Exception {
        mvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Articles")));
    }

    @Test
    void shouldListArticles() throws Exception {
        Collection<Article> articles = List.of(
                new Article(1, "www.sampleURL1.com", "Article One", "http://image1.com", "Content One", "Author One", "2023-01-01"),
                new Article(2, "www.sampleURL2.com", "Article Two", "http://image2.com", "Content Two", "Author Two", "2023-02-01")
        );
        when(this.service.getArticles()).thenReturn(articles);

        mvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Article One")))
                .andExpect(content().string(containsString("Article Two")));
    }
}