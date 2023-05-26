package com.example.demo.UserManagement.domain.services;

import com.example.demo.UserManagement.api.UserDto;

import io.reactivex.Completable;

public interface UserService {

	/**
	 *   1) Solo se deben permitir el registro de un unico usuario por su identificación *
	 *   2) Solo empleados autorizados pueden acceder al sistema *
	 *   3) El sistema debe permitir a los clientes pagar por tarjeta de credito o efectivo
	 */
	
	Completable accessControl(UserDto userDto);
}
