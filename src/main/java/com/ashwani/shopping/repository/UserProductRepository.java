package com.ashwani.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashwani.shopping.model.UserProduct;

import java.util.List;

public interface UserProductRepository extends JpaRepository<UserProduct, Long> {
    List<UserProduct> findAll();
}
