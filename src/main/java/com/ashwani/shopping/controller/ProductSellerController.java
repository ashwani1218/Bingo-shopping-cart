package com.ashwani.shopping.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ashwani.shopping.model.Product;
import com.ashwani.shopping.service.ProductService;

@RestController
public class ProductSellerController {
     
	@Autowired
	private ProductService productService;
	
	@PostMapping("/seller/products/add")
	public ModelAndView addProduct(@ModelAttribute @Valid Product product) {
		 Product addProduct = productService.addProduct(product);
		 System.out.println(" Added product "+addProduct.getName()+ "with id " + addProduct.getId());
		 return new ModelAndView("/seller/items/success");
	}
	
	@PostMapping("/seller/products/update")
	public ModelAndView updateProduct(@Valid Product product) {
		 productService.updateProduct(product);
		 return new ModelAndView("/seller/items/success");
	}
	
	@DeleteMapping("/seller/products/{productId}")
	public ModelAndView deleteProduct(@PathVariable Long productId) {
		productService.deleteProduct(productId);
		return new ModelAndView("/seller/items/success");
	}
}
