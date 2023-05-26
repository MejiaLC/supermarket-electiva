package com.example.demo.SalesProducts.domain.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.SalesProducts.domain.models.Products;

public interface ProductsRepository extends CrudRepository<Products, String> {

}
