package com.example.demo.SalesProducts.domain.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.SalesProducts.api.SalesDto;
import com.example.demo.SalesProducts.domain.models.Products;
import com.example.demo.SalesProducts.domain.repository.ProductsRepository;
import com.example.demo.SalesProducts.domain.repository.SalesRepository;

@ExtendWith(SpringExtension.class)
class SalesServiceImplTest {
	
	@Mock
	private SalesRepository salesRepository;
	
	@Mock
	private ProductsRepository productsRepository;
	
	@InjectMocks
	private SalesServiceImpl salesService;
	
	private static SalesDto salesDto;
		
	private static Products products;

	@BeforeEach
	void setUp() throws Exception {
		products = Products.builder()
				.id(1)
				.code("12345")
				.name("Libra de arroz")
				.price(4500f)
				.quantity(5)
			.build();
	
		salesDto = SalesDto.builder()
				.codigoVenta("vel012")
				.created_at(LocalDateTime.now())
				.quantity_sale(2)
				.product(products)
			.build();
	}

	@Test
	@DisplayName("El sistema debe validar que el producto exista")
	void productExistTest() {
				
		when(productsRepository.findById("454545")).thenReturn(Optional.of(products));
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			salesService.validate(products);
		});
		
		String expectedMessage = "El producto no existe";
		
		assertTrue(exception.getMessage().contains(expectedMessage));
	}
	
	@Test
	@DisplayName("Si el codigo del producto a agregar ya existe, solo modificar cantidad mas no agregar uno nuevo")
	void addProductExistTest() {
		
		when(productsRepository.findById(anyString())).thenReturn(Optional.of(products));
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			salesService.validate(products);
		});
		
		String expectedMessage = "Se ha agregado un producto que ya existe";
		
		assertTrue(exception.getMessage().contains(expectedMessage));
	}
	
	@Test
	@DisplayName("Al realizar una venta solo se pueden vender productos con stock mayor a cero")
	void validateProductStockTest() {
		
		products.setQuantity(0);
		when(productsRepository.findById(anyString())).thenReturn(Optional.of(products));
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			salesService.validate(products);
		});
		
		String expectedMessage = "No es posible vender un producto con un stock igual a cero";
		
		assertTrue(exception.getMessage().contains(expectedMessage));
	}

	@Test
	@DisplayName("A cada venta se le debe agregar un 3% del iva del producto")
	void ivaSaleTest() {
				
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			salesService.validateIva(salesDto);
		});
		
		String expectedMessage = "No es posible realizar la venta debido a que no tiene saldo suficiente para pagar el porcentaje del IVA";
		
		assertTrue(exception.getMessage().contains(expectedMessage));
	}
}
