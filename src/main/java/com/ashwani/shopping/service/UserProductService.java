package com.ashwani.shopping.service;


import com.ashwani.shopping.model.UserProduct;
import java.util.List;

public interface UserProductService {
    
    List<UserProduct> getBuyers();
    
    List<UserProduct> getProductsBoughtByUser();

    void addUserProduct(UserProduct userProduct);
}
