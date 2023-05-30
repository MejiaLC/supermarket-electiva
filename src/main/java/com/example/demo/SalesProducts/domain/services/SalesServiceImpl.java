package com.example.demo.SalesProducts.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.SalesProducts.api.SalesDto;
import com.example.demo.SalesProducts.domain.models.Products;
import com.example.demo.SalesProducts.domain.repository.ProductsRepository;

@Service
public class SalesServiceImpl implements SalesService {

	@Autowired
	private ProductsRepository productsRepository;

	@Override
	public boolean validate(Products product) {

		Products storedProduct = productsRepository.findById(product.getCode()).orElseThrow(() -> {
			throw new IllegalArgumentException("El producto no existe");
		});
		
		if(storedProduct.getQuantity() <= 0) {
			throw new IllegalArgumentException("No es posible vender un producto con un stock igual a cero");
		}
		
		productsRepository.save(product);
		Products newProduct = productsRepository.findById(product.getCode()).get();
		
		if(newProduct.getCode().equals(product.getCode())) {
			throw new IllegalArgumentException("Se ha agregado un producto que ya existe");
		}		

		return false;
	}

	@Override
	public boolean validateIva(SalesDto salesDto) {
		
		Float balance = 9000f;
		
		salesDto.setTotal(salesDto.getQuantity_sale() * salesDto.getProduct().getPrice());
				
		Float saleWithIva = salesDto.getTotal() * 1.03f;
		
		if(balance < saleWithIva) {
			throw new IllegalArgumentException("No es posible realizar la venta debido a que no tiene saldo suficiente para pagar el porcentaje del IVA");
		}
		
		
		return false;
	}

	@Override
	public void addProduct(Products product) {
		productsRepository.save(product);
	}

	@Override
	public List<Products> findAll() {
		List<Products> products = (List<Products>) productsRepository.findAll();
		return products;
	}
	
}
