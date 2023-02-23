package com.backendassignment.springbackend.repository;

import com.backendassignment.springbackend.model.SupplierEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity,Long> {
    Optional<SupplierEntity> findById(Long id);
    @Query("select s.id from SupplierEntity s where s.supplierName =?1")
    Long findSupplierIdBySupplierName(String supplierName);
}