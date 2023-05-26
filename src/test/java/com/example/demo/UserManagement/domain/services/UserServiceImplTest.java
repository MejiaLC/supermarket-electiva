package com.example.demo.UserManagement.domain.services;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.UserManagement.api.UserDto;
import com.example.demo.UserManagement.domain.models.Customers;
import com.example.demo.UserManagement.domain.models.Employees;
import com.example.demo.UserManagement.domain.models.PaymentMethods;
import com.example.demo.UserManagement.domain.repository.CustomerRepository;
import com.example.demo.UserManagement.domain.repository.EmployeeRepository;

@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
	
	@Mock
	private CustomerRepository customerRepository;
	
	@Mock
	private EmployeeRepository employeeRepository;
	
	@InjectMocks
	private UserServiceImpl userService;
	
	private static UserDto userDto;
	
	private static Customers customers;
	
	private static Employees employees;
	
	@BeforeEach 
	void setUp() throws Exception{
		customers = Customers.builder()
				.name("Pablo")
				.lastName("Pineda Sanchez")
				.phone("45213")
				.identification("1020457")
				.email("pablo12@gmail.com")	
				.paymentMethod(PaymentMethods.TARJETA_CREDITO)
			.build();
	
		employees = Employees.builder()
					.userName("nebula")
					.password("12345454")
				.build();
		
		userDto = UserDto.builder()
					.userInfo(customers)
					.employeeInfo(employees)
				.build();
	}
	
	@Test
	@DisplayName("Solo se deben permitir el registro de un unico usuario por su identificación")
	void onlyUniqueUserTest() {
		
		when(customerRepository.findById(anyString())).thenReturn(Optional.of(customers));
		
		userService.accessControl(userDto)
			.test()
			.assertNotComplete()
			.assertErrorMessage("El usuario ya existe");
	}

	@Test
	@DisplayName("Solo empleados o usuarios autorizados pueden acceder al sistema")
	void onlyAuthorizedUserTest() {
				
		when(customerRepository.findById("43434343434")).thenReturn(Optional.of(customers));
		when(employeeRepository.findById("75878979")).thenReturn(Optional.of(employees));
				
		userService.accessControl(userDto)
			.test()
			.assertNotComplete()
			.assertErrorMessage("El empleado no esta autorizado");
	}
	
	@Test
	@DisplayName("El sistema debe permitir a los clientes pagar por tarjeta de credito o efectivo")
	void customerPaymentMethodTest() {
		
		userDto.getEmployeeInfo().setUserName("rocket");

		customers.setPaymentMethod(PaymentMethods.TEST);
		
		when(customerRepository.findById("43434343434")).thenReturn(Optional.of(customers));
		when(employeeRepository.findById(anyString())).thenReturn(Optional.of(employees));
				
		userService.accessControl(userDto)
			.test()
			.assertNotComplete()
			.assertErrorMessage("Metodo de pago no permitido");
	}
	
	@Test
	@DisplayName("Todos los casos son exitosos")
	void allCasesSuccessfull() {
		
		when(customerRepository.findById("43434343434")).thenReturn(Optional.of(customers));
		when(employeeRepository.findById(anyString())).thenReturn(Optional.of(employees));
		
						
		userService.accessControl(userDto)
			.test()
			.assertComplete();
	}
}
