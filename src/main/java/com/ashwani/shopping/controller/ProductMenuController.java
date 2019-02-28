package com.ashwani.shopping.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ashwani.shopping.model.Product;
import com.ashwani.shopping.service.ProductService;

@RestController
public class ProductMenuController {
   
	@Autowired
	private ProductService productService;
	
	@GetMapping("/seller/items/add")
	public ModelAndView addProduct(@Valid Product product) {
		 ModelAndView modelAndView = new ModelAndView();
		 modelAndView.addObject("product",product);
		 return new ModelAndView("/seller/items/add");
	}
	
	@GetMapping("/seller/items/update")
	public ModelAndView updateProduct(@Valid Product product) {
		 
		 return new ModelAndView("/seller/items/update");
	}
	
	@GetMapping("/seller/items/delete/{id}")
	public ModelAndView deleteProduct(@PathVariable Long id) {
		Optional<Product> product = productService.findById(id);
		ModelAndView modelAndView = new ModelAndView("/seller/items/delete");
		modelAndView.addObject("product",product.get());
		return modelAndView;
	}
}
