package com.ashwani.shopping.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductTest {

	Product product;
	
	@BeforeEach
	void setUp() throws Exception {
		product=new Product();
	}

	@Test
	void testGetId() {
		Long id=1L;
		product.setId(id);
		assertEquals(id, product.getId());
	}

	@Test
	void testGetName() {
		String name="Ashwani";
		product.setName(name);
		assertEquals(name, product.getName());
	}

	@Test
	void testGetDescription() {
		String description="Description";
		product.setDescription(description);
		assertEquals(description, product.getDescription());;
		
	}

	@Test
	void testGetQuantity() {
		Integer quantity=20;
		product.setQuantity(quantity);
		assertEquals(quantity, product.getQuantity());
	}

	@Test
	void testGetPrice() {
		BigDecimal price=any();
		product.setPrice(price);
		assertEquals(price, product.getPrice());
		
	}

}
