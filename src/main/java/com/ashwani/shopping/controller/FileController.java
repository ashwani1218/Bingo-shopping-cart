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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ashwani.shopping.model.ProductImage;
import com.ashwani.shopping.response.model.UploadFileResponse;
import com.ashwani.shopping.service.ProductImageService;

@RestController
public class FileController {
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private ProductImageService productImageService;
	
	@PostMapping("/uploadFile")
	public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam Long productId) {
	    ProductImage productImage = productImageService.storeImageForProduct(file, productId);
	
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
}
