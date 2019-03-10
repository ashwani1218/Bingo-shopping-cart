package com.ashwani.shopping.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
		List<UserProduct> buyers = userProductService.getBuyers();
		ModelAndView modelAndView = new ModelAndView("/buyer/buyers");
		modelAndView.addObject("buyers", buyers);
		return modelAndView;
	}
	
	@GetMapping("/userproducts")
	public ModelAndView soldProducts(@PathParam("userid") String userid) {
		List<UserProduct> buyers = userProductService.getBuyers();
		ModelAndView modelAndView = new ModelAndView("/buyer/buyers");
		modelAndView.addObject("buyers", buyers);
		return modelAndView;
	}
}
