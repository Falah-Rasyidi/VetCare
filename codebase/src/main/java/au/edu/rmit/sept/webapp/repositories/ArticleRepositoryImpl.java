package au.edu.rmit.sept.webapp.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import au.edu.rmit.sept.webapp.models.Article;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {
    private DataSource source;

    public ArticleRepositoryImpl(DataSource source) {
        this.source = source;
    }

    @Override
    public List<Article> getAll() {
        try {
            Connection connection = this.source.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM vetArticles;");
            List<Article> articles = new ArrayList<>();
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                Article article = new Article(
                        results.getInt(1),
                        results.getString(2),
                        results.getString(3),
                        results.getString(4),
                        results.getString(5),
                        results.getString(6),
                        results.getString(7));
                articles.add(article);
            }
            connection.close();

            return articles;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}