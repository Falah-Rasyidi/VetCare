package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.Article;
import au.edu.rmit.sept.webapp.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleServiceImplTest {
    @Autowired
    private ArticleService service;
    @Test
    void getArticles() {
        Collection<Article> users = service.getArticles();
        assertNotNull(users);
    }
}