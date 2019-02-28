package com.ashwani.shopping.service;

import java.math.BigDecimal;
import java.util.Map;

import com.ashwani.shopping.exception.NotEnoughProductsInStockException;
import com.ashwani.shopping.model.Product;

public interface ShoppingCartService {

    void addProduct(Product product);

    void removeProduct(Product product);

    Map<Product, Integer> getProductsInCart();

    void checkout() throws NotEnoughProductsInStockException;

    BigDecimal getTotal();
}
