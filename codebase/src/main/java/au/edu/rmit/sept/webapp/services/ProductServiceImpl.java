package au.edu.rmit.sept.webapp.services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.edu.rmit.sept.webapp.models.Product;
import au.edu.rmit.sept.webapp.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository repository;

    @Autowired
    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Collection<Product> getProducts() {
        return repository.getAll();
    }
}
