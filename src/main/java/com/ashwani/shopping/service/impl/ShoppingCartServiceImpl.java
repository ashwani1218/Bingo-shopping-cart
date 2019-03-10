package com.ashwani.shopping.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.ashwani.shopping.exception.NotEnoughProductsInStockException;
import com.ashwani.shopping.model.Product;
import com.ashwani.shopping.repository.ProductRepository;
import com.ashwani.shopping.service.ShoppingCartService;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Shopping Cart is implemented with a Map, and as a session bean
 *
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ProductRepository productRepository;
    private final UserProductServiceImpl userProductServiceImpl;

    private Map<String, Map<Product,Integer>> userProductsMapping = new ConcurrentHashMap<>();

    @Autowired
    public ShoppingCartServiceImpl(ProductRepository productRepository, UserProductServiceImpl userProductServiceImpl) {
        this.productRepository = productRepository;
        this.userProductServiceImpl = userProductServiceImpl;
    }

    /**
     * If product is in the map just increment quantity by 1.
     * If product is not in the map with, add it with quantity 1
     *
     * @param product
     */
    @Override
    public void addProduct(String userId, Product product) {
    	Map<Product, Integer> products = getDefaultProductFromMapIfEmpty(userId);
        if (products.containsKey(product)) {
            products.replace(product, products.get(product) + 1);
        } else {
            products.put(product, 1);
        }
    }

    /**
     * If product is in the map with quantity > 1, just decrement quantity by 1.
     * If product is in the map with quantity 1, remove it from map
     *
     * @param product
     */
    @Override
    public void removeProduct(String userId, Product product) {
    	Map<Product, Integer> products = getDefaultProductFromMapIfEmpty(userId);
        if (products.containsKey(product)) {
            if (products.get(product) > 1)
                products.replace(product, products.get(product) - 1);
            else if (products.get(product) == 1) {
                products.remove(product);
            }
        }
    }

    /**
     * @return unmodifiable copy of the map
     */
    @Override
    public Map<Product, Integer> getProductsInCart(String userId) {
    	    System.out.println("getting products for user "+userId);
     	return getDefaultProductFromMapIfEmpty(userId);
    }

    private Map<Product, Integer> getDefaultProductFromMapIfEmpty(String userId) {
    		Map<Product, Integer> products = userProductsMapping.get(userId);
     	if(products!=null)
     		return products;
     	else {
     		Map<Product, Integer> newProductsMap = new HashMap<>();
     		userProductsMapping.put(userId, newProductsMap);
     		return newProductsMap;
     	}
    }
    /**
     * Checkout will rollback if there is not enough of some product in stock
     *
     * @throws NotEnoughProductsInStockException
     */
    @Override
    public void checkout(String userId) throws NotEnoughProductsInStockException {
    	
    	Map<Product, Integer> products = getDefaultProductFromMapIfEmpty(userId);
        Product product;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            // Refresh quantity for every product before checking
            product = productRepository.findOne(entry.getKey().getId());
            if (product.getQuantity() < entry.getValue())
                throw new NotEnoughProductsInStockException(product);
            entry.getKey().setQuantity(product.getQuantity() - entry.getValue());
        }
        productRepository.save(products.keySet());
        productRepository.flush();
        products.clear();
    }

    @Override
    public BigDecimal getTotal(String userId) {
    	 Map<Product, Integer> products = getDefaultProductFromMapIfEmpty(userId);
        return products.entrySet().stream()
                .map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}


















