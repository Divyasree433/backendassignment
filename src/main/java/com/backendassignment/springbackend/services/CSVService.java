package com.backendassignment.springbackend.services;
import com.backendassignment.springbackend.repository.ProductSupplierRepository;
import com.backendassignment.springbackend.repository.ProductRepository;
import com.backendassignment.springbackend.repository.SupplierRepository;
import com.backendassignment.springbackend.model.ProductSupplierEntity;
import com.backendassignment.springbackend.model.ProductEntity;
import com.backendassignment.springbackend.model.SupplierEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import static com.backendassignment.springbackend.SpringbackendApplication.productMap;
import static com.backendassignment.springbackend.SpringbackendApplication.supplierMap;

@Service
public class CSVService {
    private final ProductSupplierRepository inventoryDataRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;

    public CSVService(ProductSupplierRepository inventoryDataRepository, ProductRepository productRepository, SupplierRepository supplierRepository) {
        this.inventoryDataRepository = inventoryDataRepository;
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
    }
    public void save(MultipartFile file){
        try{
            List[] outputLists=csvToInventories(file.getInputStream());
            productRepository.saveAll(outputLists[0]);
            supplierRepository.saveAll(outputLists[1]);
            inventoryDataRepository.saveAll(outputLists[2]);
        }
        catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }
    public static String TYPE = "text/csv";
    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }
    public static List[] csvToInventories(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<ProductSupplierEntity> inventoryDataList = new ArrayList<>();
            List<ProductEntity> productsList = new ArrayList<>();
            List<SupplierEntity> suppliersList = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                ProductEntity product;
                String code = csvRecord.get(0);

                if (productMap.containsKey(code)) {
                    product = productMap.get(code);
                } else {
                    product = new ProductEntity(
                            code,
                            csvRecord.get("name"),
                            csvRecord.get("company")
                    );
                    productMap.put(product.getCode(), product);
                }

                SupplierEntity supplier;
                String supplierName = csvRecord.get("supplier");
                if (supplierMap.containsKey(supplierName)) {
                    supplier = supplierMap.get(supplierName);
                } else {
                    supplier = new SupplierEntity(csvRecord.get("supplier"));
                    supplierMap.put(supplier.getSupplierName(), supplier);
                }
                ProductSupplierEntity inventoryData = new ProductSupplierEntity(
                        product,
                        csvRecord.get("batch"),
                        Integer.parseInt(csvRecord.get("stock")),
                        Integer.parseInt(csvRecord.get("deal")),
                        Integer.parseInt(csvRecord.get("free")),
                        Float.parseFloat(csvRecord.get("mrp")),
                        Float.parseFloat(csvRecord.get("rate")),
                        supplier
                );
                if (csvRecord.get("exp").matches("([0-9]{2})-([0-9]{2})-([0-9]{4})")) {
                    inventoryData.setExpiry(new SimpleDateFormat("dd-MM-yyyy").parse(csvRecord.get("exp")));
                }
                supplier.getInventoryData().add(inventoryData);
                inventoryData.setSupplier(supplier);

                productsList.add(product);
                suppliersList.add(supplier);
                inventoryDataList.add(inventoryData);
            }

            return new List[]{productsList, suppliersList, inventoryDataList};
        } catch (IOException | ParseException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
