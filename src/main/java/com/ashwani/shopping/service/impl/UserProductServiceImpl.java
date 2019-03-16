package com.ashwani.shopping.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashwani.shopping.model.Product;
import com.ashwani.shopping.model.UserProduct;
import com.ashwani.shopping.repository.UserProductRepository;
import com.ashwani.shopping.service.UserProductService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Service
public class UserProductServiceImpl implements UserProductService {

    private final UserProductRepository userProductRepository;
    
    @Autowired
    public UserProductServiceImpl (UserProductRepository userProductRepository) {
    	   this.userProductRepository = userProductRepository;
    }

	@Override
	public List<UserProduct> getProductsBoughtByUser(String userId) {
		List<UserProduct>  allUserproducts = userProductRepository.findAll();
		List<UserProduct>  userProducts=new ArrayList<>();
		for(UserProduct userProduct: allUserproducts) {
			if(userProduct.getUser_id().equals(userId)) {
				userProducts.add(userProduct);
			}
		}
		return userProducts;
	}

	@Override
	public List<UserProduct> getBuyers() {
		List<UserProduct>  userproducts = userProductRepository.findAll();
		return userproducts;
	}

	@Override
	public void addUserProduct(String userId, Map<Product, Integer> products) {
		Iterator<Entry<Product, Integer>> iterator = products.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<Product, Integer> entry =  iterator.next();
			Product product  = entry.getKey();
			Integer count = entry.getValue();
			if(product != null) {
					UserProduct userProduct = new UserProduct();
					userProduct.setUser_id(userId);
					userProduct.setProduct_name(product.getName());
					userProduct.setQuantity(count);
					userProduct.setPrice(product.getPrice());
					userProductRepository.save(userProduct);
			}
		}
	}
 
}
