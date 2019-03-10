package com.ashwani.shopping.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ashwani.shopping.model.Product;
import com.ashwani.shopping.model.UserProduct;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<Product> findById(Long id);

    Page<Product> findAllProductsPageable(Pageable pageable);
    
    Product addProduct(Product product);
    
    Product updateProduct(Product product);
    
    void deleteProduct(Long id);

}
