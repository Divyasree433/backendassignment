package com.backendassignment.springbackend.controllers;

import com.backendassignment.springbackend.model.ProductEntity;
import com.backendassignment.springbackend.model.ProductSupplierEntity;
import com.backendassignment.springbackend.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/csv")
public class SupplierController {
    private SearchService searchService;

    @Autowired
    public SupplierController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("supplier/{supplier}")
    Page<ProductSupplierEntity> getInventories(@PathVariable String supplier,
                                               @RequestParam(defaultValue = "") String productName,
                                               @RequestParam(defaultValue = "false") Boolean notExpired,
                                               @RequestParam(defaultValue = "0")int offset,
                                               @RequestParam(defaultValue = "5") int pageSize){
        try{
            Long supplierId = Long.parseLong(supplier);
            return searchService.getInventoryBySupplierId(supplierId,productName,notExpired,offset,pageSize);
        }
        catch(Exception e){
            Long supplierId = searchService.getSupplierIdBySupplierName(supplier);
            return searchService.getInventoryBySupplierId(supplierId,productName,notExpired,offset,pageSize);
        }

    }
}
