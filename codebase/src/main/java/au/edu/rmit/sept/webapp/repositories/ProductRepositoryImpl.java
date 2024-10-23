package au.edu.rmit.sept.webapp.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

//import org.springframework.jdbc.datasource.init.UncategorizedScriptException;
import org.springframework.stereotype.Repository;

import au.edu.rmit.sept.webapp.models.Product;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private DataSource source;

    public ProductRepositoryImpl(DataSource source) {
        this.source = source;
    }

    @Override
    public List<Product> getAll() {
        try {
            Connection connection = this.source.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT itemName, description, quantity, price, specialPrice FROM product ORDER BY specialPrice DESC;");
            List<Product> products = new ArrayList<>();
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                Product product = new Product(results.getString(1), results.getString(2), results.getInt(3), results.getDouble(4), results.getDouble(5));
                products.add(product);
            }
            connection.close();

            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
