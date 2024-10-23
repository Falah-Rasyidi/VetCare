package au.edu.rmit.sept.webapp.repositories;

import java.util.List;
import au.edu.rmit.sept.webapp.models.Product;

public interface ProductRepository {
    public List<Product> getAll();
}
