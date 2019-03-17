package com.ashwani.shopping.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "product_imgs")
public class ProductImage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "img_id")
    private Long imgId;

	@Column(name = "product_id", nullable = false)
    private Long productId;
	
	@Column(name = "img_title", nullable = false)
    private String img_title;

	@Column(name = "content_type", nullable = false)
    private String content_type;
	
    @Lob
    @Column(name = "img_data", nullable = false)
    private byte[] img_data;

    public ProductImage() {

    }

	public ProductImage(Long productId, String img_title, String content_type, byte[] img_data) {
		super();
		this.productId = productId;
		this.img_title = img_title;
		this.content_type = content_type;
		this.img_data = img_data;
	}

	public Long getImgId() {
		return imgId;
	}

	public void setImgId(Long imgId) {
		this.imgId = imgId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getContent_type() {
		return content_type;
	}

	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}

	public String getImg_title() {
		return img_title;
	}

	public void setImg_title(String img_title) {
		this.img_title = img_title;
	}

	public byte[] getImg_data() {
		return img_data;
	}

	public void setImg_data(byte[] img_data) {
		this.img_data = img_data;
	}
}
