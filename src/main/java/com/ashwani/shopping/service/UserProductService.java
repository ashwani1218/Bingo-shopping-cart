package com.ashwani.shopping.service;


import com.ashwani.shopping.model.Product;
import com.ashwani.shopping.model.UserProduct;

import java.util.List;
import java.util.Map;

public interface UserProductService {
    
    List<UserProduct> getBuyers();
    
    List<UserProduct> getProductsBoughtByUser();

    void addUserProduct(String userId, Map<Product, Integer> products);
}
