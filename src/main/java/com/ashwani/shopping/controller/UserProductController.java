package com.ashwani.shopping.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.ashwani.shopping.model.UserProduct;
import com.ashwani.shopping.service.UserProductService;

@RestController
public class UserProductController {
     
	@Autowired
	private UserProductService userProductService;
	
	@GetMapping("/buyers")
	public ModelAndView buyers() {
		List<UserProduct> userProducts = userProductService.getBuyers();
		Set<String> buyers = new HashSet<>();
		for(UserProduct userProduct:userProducts) {
			buyers.add(userProduct.getUser_id());
		}
		ModelAndView modelAndView = new ModelAndView("/buyer/buyers");
		modelAndView.addObject("buyers", buyers);
		return modelAndView;
	}
	
	@GetMapping("/userproducts/{userid}")
	public ModelAndView soldProducts(@PathVariable("userid") String userid) {
		List<UserProduct> userproducts = userProductService.getProductsBoughtByUser(userid);
		ModelAndView modelAndView = new ModelAndView("/buyer/soldproducts");
		modelAndView.addObject("userproducts", userproducts);
		return modelAndView;
	}
}
