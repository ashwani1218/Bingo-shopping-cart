package com.ashwani.shopping.controller;

import com.ashwani.shopping.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;

import com.paypal.base.rest.PayPalRESTException;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.ashwani.shopping.exception.NotEnoughProductsInStockException;
import com.ashwani.shopping.model.Product;
import com.ashwani.shopping.service.ProductService;
import com.ashwani.shopping.service.ShoppingCartService;

@Controller
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    private final ProductService productService;


    @Autowired
    private PaypalService paypalService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService, ProductService productService) {
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
    }

    @GetMapping("/shoppingCart/{userId}")
    public ModelAndView shoppingCart(@PathVariable String userId) {
        ModelAndView modelAndView = new ModelAndView("/shoppingCart");
        modelAndView.addObject("products", shoppingCartService.getProductsInCart(userId));
        modelAndView.addObject("total", shoppingCartService.getTotal(userId).toString());
        return modelAndView;
    }

    @GetMapping("/shoppingCart/addProduct/{productId}/{userId}")
    public ModelAndView addProductToCart(@PathVariable("userId") String userId, @PathVariable("productId") Long productId) {
        Optional<Product> findById = productService.findById(productId);
        
        if(findById.isPresent()) {
        		shoppingCartService.addProduct(userId, findById.get());
        }
        return shoppingCart(userId);
    }

    @GetMapping("/shoppingCart/removeProduct/{productId}/{userId}")
    public ModelAndView removeProductFromCart(@PathVariable("userId") String userId, @PathVariable("productId") Long productId) {
    	    Optional<Product> findById = productService.findById(productId);
         
         if(findById.isPresent()) {
         		shoppingCartService.removeProduct(userId, findById.get());
         }
         return shoppingCart(userId);
    }

    @GetMapping("/shoppingCart/checkout/{userId}")
    public ModelAndView checkout(@PathVariable("userId") String userId) {
        try {
        	System.out.println("user id in checkout is "+userId);
            shoppingCartService.checkout(userId);
        } catch (NotEnoughProductsInStockException e) {
            return shoppingCart(userId).addObject("outOfStockMessage", e.getMessage());
        }
        return shoppingCart(userId);
    }

    @GetMapping("/pay/{userId}")
    public String makePay(@RequestParam(required = false) String total, @PathVariable("userId") String userId) {

        System.out.println("Processing makePay amount " +total + " for user "+userId);

        total = StringUtils.isEmpty(total) ? "0.05" : total;
        Payment payment = null;
        try {

            payment = paypalService.createPayment(
                    Double.parseDouble(total), userId);
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }

        for (Links links : payment.getLinks()) {
            if (links.getRel().equals("approval_url")) {
                return "redirect:" + links.getHref();
            }
        }

        return "redirect:/";
    }


    @RequestMapping(method = RequestMethod.GET, value = "pay/cancel")
    public ModelAndView cancelPay() {
        ModelAndView modelAndView = new ModelAndView("/cancel");
        return modelAndView;
    }


    @RequestMapping(method = RequestMethod.GET, value = "pay/success")
    public ModelAndView successPay(@RequestParam("userId") String userId, @RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {

            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                //populate the search results in model attribute and forward to success so that
                // success page can display search results.
                System.out.println("Successfully processing payment:"+payment.getId());
                shoppingCartService.checkout(userId);

            }
        } catch (Exception e) {

        }
        ModelAndView modelAndView = new ModelAndView("/success");
        return modelAndView;
    }

}