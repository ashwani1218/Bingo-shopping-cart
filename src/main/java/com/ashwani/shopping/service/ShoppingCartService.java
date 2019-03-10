package com.ashwani.shopping.service;

import java.math.BigDecimal;
import java.util.Map;

import com.ashwani.shopping.exception.NotEnoughProductsInStockException;
import com.ashwani.shopping.model.Product;

public interface ShoppingCartService {

    void addProduct(String userId, Product productId);

    void removeProduct(String userId, Product productId);

    Map<Product, Integer> getProductsInCart(String userId);

    void checkout(String userId) throws NotEnoughProductsInStockException;

    BigDecimal getTotal(String userId);
}
