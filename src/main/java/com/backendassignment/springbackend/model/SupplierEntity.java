package com.backendassignment.springbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class SupplierEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String supplierName;

    @OneToMany(mappedBy = "supplier")
    @JsonIgnore
    private List<ProductSupplierEntity> inventoryData = new ArrayList<>();

    public SupplierEntity(String supplierName) {
        this.supplierName = supplierName;
    }
    public String getSupplierName() {
        return supplierName;
    }

    public List<ProductSupplierEntity> getInventoryData() {
        return inventoryData;
    }

}
