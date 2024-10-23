package au.edu.rmit.sept.webapp.repositories;

import java.util.List;
import au.edu.rmit.sept.webapp.models.Article;

public interface ArticleRepository {
    public List<Article> getAll();
}
