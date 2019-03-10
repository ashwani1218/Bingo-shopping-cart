package com.ashwani.shopping.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashwani.shopping.model.UserProduct;
import com.ashwani.shopping.repository.UserProductRepository;
import com.ashwani.shopping.service.UserProductService;
import java.util.List;

@Service
public class UserProductServiceImpl implements UserProductService {

    private final UserProductRepository userProductRepository;
    
    @Autowired
    public UserProductServiceImpl (UserProductRepository userProductRepository) {
    	   this.userProductRepository = userProductRepository;
    }

	@Override
	public List<UserProduct> getBuyers() {
		List<UserProduct>  userproducts = userProductRepository.findAll();
		return userproducts;
	}

	@Override
	public List<UserProduct> getProductsBoughtByUser() {
		List<UserProduct>  userproducts = userProductRepository.findAll();
		return userproducts;
	}

	@Override
	public void addUserProduct(UserProduct userProduct) {
		userProductRepository.save(userProduct);
	}
}
