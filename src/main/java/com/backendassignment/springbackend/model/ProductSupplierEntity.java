package com.backendassignment.springbackend.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class ProductSupplierEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private ProductEntity product;

    private String batch;
    private Integer stock;
    private Integer deal;
    private Integer free;
    private Float mrp;
    private Float rate;
    private Date expiry;

    @ManyToOne
    private SupplierEntity supplier;

    public ProductSupplierEntity(ProductEntity product, String batch, Integer stock, Integer deal, Integer free, Float mrp, Float rate, SupplierEntity supplier) {
        this.product = product;
        this.batch = batch;
        this.stock = stock;
        this.deal = deal;
        this.free = free;
        this.mrp = mrp;
        this.rate = rate;
        this.supplier = supplier;
    }

    public ProductSupplierEntity() {

    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }
    public void setSupplier(SupplierEntity supplier) {
        this.supplier = supplier;
    }
}
