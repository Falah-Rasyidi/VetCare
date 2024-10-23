package au.edu.rmit.sept.webapp.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import au.edu.rmit.sept.webapp.models.Article;
import au.edu.rmit.sept.webapp.services.ArticleService;

@Controller
//@RequestMapping("/articles")
public class ArticlesController {

    private ArticleService service;

    @Autowired
    public ArticlesController(ArticleService service) {
        this.service = service;
    }

    @GetMapping("/articles")
    public String articles(Model model) {
        Collection<Article> articles = service.getArticles();
        model.addAttribute("articles", articles);
        return "articles/articles";
    }
}