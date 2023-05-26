package com.example.demo.SalesProducts.domain.services;

import com.example.demo.SalesProducts.api.SalesDto;
import com.example.demo.SalesProducts.domain.models.Products;

public interface SalesService {

	/**
	 * 	 1) El sistema debe validar que el producto exista *
	 *   2) Si se va a agregar un producto que ya existe solo se debe modificar la cantidad *
	 *   3) Al realizar una venta solo se pueden vender productos con stock mayor a cero *
	 *   4) Cada venta tendra un IVA del 3%
	 */
	
	boolean validate(Products product);
	boolean validateIva(SalesDto salesDto);
}
