package com.ashwani.shopping.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashwani.shopping.model.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
	Optional<ProductImage> findByProductId(Long productId);
	Optional<ProductImage> findByImgId(Long imgId);
}
