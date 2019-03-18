package com.ashwani.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ashwani.shopping.model.ProductImage;
import com.ashwani.shopping.response.model.UploadFileResponse;
import com.ashwani.shopping.service.ProductImageService;

@RestController
public class FileController {
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private ProductImageService productImageService;
	
	@GetMapping("/uploadFile/{productId}")
	public ModelAndView loadFileUploadFeature(@PathVariable("productId") String productId) {
		logger.info("loading fileupload for product "+productId);
		ModelAndView modelAndView = new ModelAndView("/fileupload");
		modelAndView.addObject("productId", productId);
		return modelAndView;
	}
	
	@PostMapping("/uploadFile/{productId}")
	public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("productId") String productId) {
		
		logger.info("uploading image for product id"+productId);
	    ProductImage productImage = productImageService.storeImageForProduct(file, Long.valueOf(productId));
	
	    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	            .path("/downloadFile/")
	            .path(productImage.getImgId().toString())
	            .toUriString();
	
	    return new UploadFileResponse(productImage.getImg_title(), fileDownloadUri,
	            file.getContentType(), file.getSize());
	}
	
	@GetMapping("/downloadFile/{fileId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
	    // Load file from database
	    ProductImage productImage = productImageService.getImage(fileId);
	
	    return ResponseEntity.ok()
	            .contentType(MediaType.parseMediaType(productImage.getContent_type()))
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + productImage.getImg_title() + "\"")
	            .body(new ByteArrayResource(productImage.getImg_data()));
	}
	
	@GetMapping("/images/{productId}")
	public ResponseEntity<Resource> downloadImageForProduct(@PathVariable String productId) {
	    // Load file from database
	    ProductImage productImage = productImageService.getImageForProduct(Long.valueOf(productId));
        if( productImage!=null ) {
        		return ResponseEntity.ok()
    	            .contentType(MediaType.parseMediaType(productImage.getContent_type()))
    	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + productImage.getImg_title() + "\"")
    	            .body(new ByteArrayResource(productImage.getImg_data()));	
        }else {
        		return null;
        }
	}
}
