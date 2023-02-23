package com.backendassignment.springbackend.repository;

import com.backendassignment.springbackend.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    Optional<ProductEntity> findById(Long id);
}

