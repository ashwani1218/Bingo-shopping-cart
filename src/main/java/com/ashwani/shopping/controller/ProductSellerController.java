package com.ashwani.shopping.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ashwani.shopping.model.Product;
import com.ashwani.shopping.service.ProductService;

@RestController("/seller")
public class ProductSellerController {
     
	@Autowired
	private ProductService productService;
	
	@PostMapping("/products/add")
	public ModelAndView addProduct(@Valid Product product) {
		 productService.addProduct(product);
		 return new ModelAndView("products/sucess");
	}
	
	@PostMapping("/products/update")
	public ModelAndView updateProduct(@Valid Product product) {
		 productService.updateProduct(product);
		 return new ModelAndView("products/sucess");
	}
	
	@DeleteMapping("/products/{productId}")
	public ModelAndView deleteProduct(@PathVariable Long productId) {
		productService.deleteProduct(productId);
		return new ModelAndView("products/sucess");
	}
}
