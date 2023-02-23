package com.backendassignment.springbackend.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.SessionFactory;

import java.util.List;


@Entity
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private String company;
    public ProductEntity(String code, String name, String company) {
        this.code = code;
        this.name = name;
        this.company = company;
    }
    public ProductEntity() {
    }
    public String getCode() {
        return code;
    }


}
