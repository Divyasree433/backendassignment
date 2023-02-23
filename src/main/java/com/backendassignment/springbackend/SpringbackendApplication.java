package com.backendassignment.springbackend;
import com.backendassignment.springbackend.model.ProductEntity;
import com.backendassignment.springbackend.model.SupplierEntity;
import com.backendassignment.springbackend.repository.ProductRepository;
import com.backendassignment.springbackend.repository.SupplierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class SpringbackendApplication {
	public static Map<String, ProductEntity> productMap = new HashMap<>();
	public static Map<String, SupplierEntity> supplierMap = new HashMap<>();
	private static final Logger logger = LoggerFactory.getLogger(SpringbackendApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(SpringbackendApplication.class, args);
	}
	public CommandLineRunner demo(ProductRepository prodrepository, SupplierRepository suprepository) {
		return (args) -> {
			for (ProductEntity product : prodrepository.findAll()) {
			  productMap.put(product.getCode(),product);
			}
			for (SupplierEntity supplier : suprepository.findAll()) {
				supplierMap.put(supplier.getSupplierName(), supplier);
			}
		};
	}

}
