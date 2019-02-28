package com.ashwani.shopping.controller;

import com.ashwani.shopping.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;

import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.ashwani.shopping.exception.NotEnoughProductsInStockException;
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

    @GetMapping("/shoppingCart")
    public ModelAndView shoppingCart() {
        ModelAndView modelAndView = new ModelAndView("/shoppingCart");
        modelAndView.addObject("products", shoppingCartService.getProductsInCart());
        modelAndView.addObject("total", shoppingCartService.getTotal().toString());
        return modelAndView;
    }

    @GetMapping("/shoppingCart/addProduct/{productId}")
    public ModelAndView addProductToCart(@PathVariable("productId") Long productId) {
        productService.findById(productId).ifPresent(shoppingCartService::addProduct);
        return shoppingCart();
    }

    @GetMapping("/shoppingCart/removeProduct/{productId}")
    public ModelAndView removeProductFromCart(@PathVariable("productId") Long productId) {
        productService.findById(productId).ifPresent(shoppingCartService::removeProduct);
        return shoppingCart();
    }

    @GetMapping("/shoppingCart/checkout")
    public ModelAndView checkout() {
        try {
            shoppingCartService.checkout();
        } catch (NotEnoughProductsInStockException e) {
            return shoppingCart().addObject("outOfStockMessage", e.getMessage());
        }
        return shoppingCart();
    }

    @GetMapping("/pay")
    public String makePay(@RequestParam(required = false) String total) {

        System.out.println("Processing makePay amount " +total);

        total = StringUtils.isEmpty(total) ? "0.05" : total;
        Payment payment = null;
        try {

            payment = paypalService.createPayment(
                    Double.parseDouble(total));
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
    public ModelAndView successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {

            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                //populate the search results in model attribute and forward to success so that
                // success page can display search results.
                System.out.println("Successfully processing payment:"+payment.getId());
                shoppingCartService.checkout();

            }
        } catch (Exception e) {

        }
        ModelAndView modelAndView = new ModelAndView("/success");
        return modelAndView;
    }

}