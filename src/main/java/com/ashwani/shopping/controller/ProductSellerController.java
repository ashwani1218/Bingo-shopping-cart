package com.ashwani.shopping.controller;


import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
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
		 Product addedProduct = productService.addProduct(product);
		 System.out.println(" Added product "+addedProduct.getName()+ "with id " + addedProduct.getId());
		
		 ModelAndView modelAndView = new ModelAndView("/seller/items/success");
		 modelAndView.addObject("action", "added");
		 modelAndView.addObject("product",addedProduct);
		 return modelAndView;
	}
	
	@PostMapping("/seller/products/update")
	public ModelAndView updateProduct(@Valid Product product) {
		 Product updatedProduct = productService.updateProduct(product);
		 System.out.println(" Updated product "+updatedProduct.getName()+ "with id " + updatedProduct.getId());
		
		 ModelAndView modelAndView = new ModelAndView("/seller/items/success");
		 modelAndView.addObject("action", "updated");
		 modelAndView.addObject("product",updatedProduct);
		 return modelAndView;
	}
	
	@PostMapping("/seller/products/{productId}")
	public ModelAndView deleteProduct(@ModelAttribute Product product) {
		 productService.deleteProduct(product.getId());
		 ModelAndView modelAndView = new ModelAndView("/seller/items/success");
		 modelAndView.addObject("action", "deleted");
		return modelAndView;
	}
}
