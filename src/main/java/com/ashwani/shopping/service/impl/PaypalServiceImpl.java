package com.ashwani.shopping.service.impl;

import com.ashwani.shopping.config.PaypalConfig;
import com.ashwani.shopping.service.PaypalService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PaypalServiceImpl implements PaypalService {

    @Autowired
    private APIContext apiContext;

    @Value("${base.url}")
    public String baseUrl;

    @Value("${success.url}")
    public String successUrl;

    @Value("${cancel.url}")
    public String cancelUrl;

    @Autowired
    private PaypalConfig paypalConfig;

    public Payment createPayment(Double total) throws PayPalRESTException {
       // apiContext.setMaskRequestId(false);
        Map<String,String> map = new HashMap<>();
        map.put("PayPal-Request-Id", UUID.randomUUID().toString());
        apiContext.setHTTPHeaders(map);
        //setting the amount for the transaction
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(String.format("%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription("pay_testing_shankar");
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        //setting up the urls
        RedirectUrls redirectUrls = new RedirectUrls();

        String cancelUrl = baseUrl + "/" + this.cancelUrl + "?";
        String successURL = baseUrl + "/" + successUrl + "?";
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successURL);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }


    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        System.out.println();
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }
}