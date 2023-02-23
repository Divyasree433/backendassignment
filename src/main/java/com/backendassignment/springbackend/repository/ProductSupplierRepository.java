package com.backendassignment.springbackend.repository;

import com.backendassignment.springbackend.model.ProductSupplierEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSupplierRepository extends JpaRepository<ProductSupplierEntity,Long> {
    @Query("select i from ProductSupplierEntity i where i.supplier in (select s.id from SupplierEntity s where s.id=?1) and i.stock>0")
    Page<ProductSupplierEntity> findBySupplierOnly(Long id, PageRequest of);

    @Query("select i from ProductSupplierEntity i where i.supplier in (select s.id from SupplierEntity s where s.id=?1) and i.product in (select p from ProductEntity p where p.name=?2) and i.stock>0")
    Page<ProductSupplierEntity> findBySupplierAndProduct(Long id, String productName, PageRequest of);

    @Query("select i from ProductSupplierEntity i where i.supplier in (select s.id from SupplierEntity s where s.id=?1) and i.expiry>now()")
    Page<ProductSupplierEntity> findBySupplierAndExpiredStatus(Long id, Boolean notExpired, PageRequest of);

    @Query("select i from ProductSupplierEntity i where i.supplier in (select s.id from SupplierEntity s where s.id = ?1 ) and i.product in (select p.id from ProductEntity p where p.name like %?2%) and i.expiry>now() and i.stock>0")
    Page<ProductSupplierEntity> findBySupplierWithAllParams(Long id, String productName, Boolean notExpired, PageRequest of);
}