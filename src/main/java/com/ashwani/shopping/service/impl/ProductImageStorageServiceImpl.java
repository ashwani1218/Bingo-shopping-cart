package com.ashwani.shopping.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.ashwani.shopping.exception.FileStorageException;
import com.ashwani.shopping.model.ProductImage;
import com.ashwani.shopping.repository.ProductImageRepository;
import com.ashwani.shopping.service.ProductImageService;
import java.io.IOException;
import java.util.Optional;

@Service
public class ProductImageStorageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;

    @Autowired
    public ProductImageStorageServiceImpl(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

	@Override
	public ProductImage storeImageForProduct(MultipartFile file, Long product_id) {
		
		// Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // String product_id, String img_title, byte[] img_data
            ProductImage dbFile = new ProductImage(product_id, fileName,file.getContentType(),  file.getBytes());

            return productImageRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
	}

	@Override
	public ProductImage getImageForProduct(Long productId) {
		Optional<ProductImage> findByProductId = productImageRepository.findByProductId(productId);
		return findByProductId.isPresent() ? findByProductId.get() : null;
	}

	@Override
	public ProductImage getImage(Long imgId) {
		Optional<ProductImage> findByImgId = productImageRepository.findByImgId(imgId);
		return findByImgId.isPresent() ? findByImgId.get() : null;
		
	}

}
