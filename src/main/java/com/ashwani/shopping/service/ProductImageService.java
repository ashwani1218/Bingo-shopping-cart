package com.ashwani.shopping.service;

import org.springframework.web.multipart.MultipartFile;

import com.ashwani.shopping.model.ProductImage;

public interface ProductImageService {

	public ProductImage storeImageForProduct(MultipartFile file, Long product_id);
	
	public ProductImage getImageForProduct(Long product_id);
	
	public ProductImage getImage(Long image_id);
}
