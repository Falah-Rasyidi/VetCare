package au.edu.rmit.sept.webapp.services;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.edu.rmit.sept.webapp.models.Article;
import au.edu.rmit.sept.webapp.repositories.ArticleRepository;

@Service
public class ArticleServiceImpl implements ArticleService {
    private ArticleRepository repository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Collection<Article> getArticles() {
        return repository.getAll();
    }
}