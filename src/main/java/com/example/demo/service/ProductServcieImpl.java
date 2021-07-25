package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServcieImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {

        Optional<Product> productdb = this.productRepository.findById(product.getId());

        if (productdb.isPresent()) {
            Product productUpdate = productdb.get();
            productUpdate = product;
            productRepository.save(productUpdate);
            return productUpdate;
        } else {
            throw new ResourceNotFoundException("Product not found with id:" + product.getId());
        }
    }

    @Override
    public List<Product> getAllProduct() {
        return this.productRepository.findAll();
    }

    @Override
    public Product getProductById(long productId) {
        Optional<Product> productdb = this.productRepository.findById(productId);

        if (productdb.isPresent()) {
            return productdb.get();
        } else {
            throw new ResourceNotFoundException("Product not found with id:" + productId);
        }
    }

    @Override
    public void deleteProduct(long productId) {
        Optional<Product> productdb = this.productRepository.findById(productId);

        if (productdb.isPresent()) {
            Product productToDelete = productdb.get();
            productRepository.delete(productToDelete);
        } else {
            throw new ResourceNotFoundException("Product not found with id:" + productId);
        }
    }
}
