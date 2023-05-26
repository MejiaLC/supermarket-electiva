package com.example.demo.SalesProducts.domain.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.SalesProducts.domain.models.Sales;

public interface SalesRepository extends CrudRepository<Sales, String> {

}
