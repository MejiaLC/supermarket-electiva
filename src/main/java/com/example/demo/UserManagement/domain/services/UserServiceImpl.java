package com.example.demo.UserManagement.domain.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.UserManagement.api.UserDto;
import com.example.demo.UserManagement.domain.models.PaymentMethods;
import com.example.demo.UserManagement.domain.repository.CustomerRepository;
import com.example.demo.UserManagement.domain.repository.EmployeeRepository;

import io.reactivex.Completable;
import io.reactivex.Single;

public class UserServiceImpl implements UserService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Completable accessControl(UserDto userDto) {
		
		return Single.just(userDto)
				.map(dto -> {
					if(customerRepository.findById(userDto.getUserInfo().getIdentification()).isPresent()) {
						throw new IllegalArgumentException("El usuario ya existe");
					}
					
					return dto;
				})
				.map(dto -> {
					if(!employeeRepository.findById(userDto.getEmployeeInfo().getUserName()).isPresent()) {
						throw new IllegalArgumentException("El empleado no esta autorizado");
					}
					
					return dto;
				})
				.map(dto -> {
					
					if(userDto.getUserInfo().getPaymentMethod().equals(PaymentMethods.TEST)) {
						throw new IllegalArgumentException("Metodo de pago no permitido");
					}
					
					return dto;
				})
				.flatMapCompletable(c -> {	
					return Completable.complete();
				});
	
	}

}
