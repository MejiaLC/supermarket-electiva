package com.example.demo.UserManagement.domain.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.UserManagement.domain.models.Customers;

public interface CustomerRepository extends CrudRepository<Customers, String> {

}
