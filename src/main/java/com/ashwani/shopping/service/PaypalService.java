package com.ashwani.shopping.service;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

public interface PaypalService {

     Payment createPayment(Double total) throws PayPalRESTException;


     Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;
}