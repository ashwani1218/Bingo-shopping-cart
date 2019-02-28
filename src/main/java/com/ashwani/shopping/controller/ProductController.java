package com.ashwani.shopping.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashwani.shopping.model.Product;
import com.ashwani.shopping.service.ProductService;

@RestController("/seller")
public class ProductController {
     
	@Autowired
	private ProductService productService;
	
	@PostMapping("/products/add")
	public boolean addProduct(@Valid Product product) {
		 productService.addProduct(product);
		 return true;
	}
	
	@PostMapping("/products/update")
	public boolean updateProduct(@Valid Product product) {
		 productService.updateProduct(product);
		 return true;
	}
	
	@DeleteMapping("/products/{productId}")
	public boolean deleteProduct(@PathVariable Long productId) {
		productService.deleteProduct(productId);
		return true;
	}
}
