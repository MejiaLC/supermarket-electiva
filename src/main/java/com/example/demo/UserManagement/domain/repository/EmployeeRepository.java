package com.example.demo.UserManagement.domain.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.UserManagement.domain.models.Employees;

public interface EmployeeRepository extends CrudRepository<Employees, String> {

}
