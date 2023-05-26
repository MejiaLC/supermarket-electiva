package com.example.demo.UserManagement.api;

import com.example.demo.UserManagement.domain.models.Customers;
import com.example.demo.UserManagement.domain.models.Employees;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private Customers userInfo;
	private Employees employeeInfo;
}
