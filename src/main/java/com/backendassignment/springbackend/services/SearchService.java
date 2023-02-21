package com.backendassignment.springbackend.services;

import com.backendassignment.springbackend.model.ProductSupplierEntity;
import com.backendassignment.springbackend.repository.ProductSupplierRepository;
import com.backendassignment.springbackend.repository.SupplierRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    private final SupplierRepository supplierRepository;
    private final ProductSupplierRepository inventoryDataRepository;
    public SearchService(SupplierRepository supplierRepository, ProductSupplierRepository inventoryDataRepository) {
        this.supplierRepository = supplierRepository;
        this.inventoryDataRepository = inventoryDataRepository;
    }
    public Page<ProductSupplierEntity> getInventoryBySupplierId(Long id, String productName, Boolean notExpired, int offset, int pageSize){
        if(productName.equals("") && !notExpired){
            return inventoryDataRepository.findBySupplierOnly(id, PageRequest.of(offset,pageSize));
        }
        else if(!productName.equals("") && !notExpired){
            return inventoryDataRepository.findBySupplierAndProduct(id,productName,PageRequest.of(offset,pageSize));
        }
        else if(productName.equals("")){
            return inventoryDataRepository.findBySupplierAndExpiredStatus(id, true,PageRequest.of(offset,pageSize));
        }
        else {
            return inventoryDataRepository.findBySupplierWithAllParams(id, productName, true,PageRequest.of(offset,pageSize));
        }
    }
    public Long getSupplierIdBySupplierName(String supplierName){
        return supplierRepository.findSupplierIdBySupplierName(supplierName);
    }
}
