package com.backendassignment.springbackend.repository;

import com.backendassignment.springbackend.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    Optional<ProductEntity> findById(Long id);
}

