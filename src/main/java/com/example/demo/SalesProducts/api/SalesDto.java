package com.example.demo.SalesProducts.api;

import java.time.LocalDateTime;

import com.example.demo.SalesProducts.domain.models.Products;

import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesDto {

	private String codigoVenta;
	private LocalDateTime created_at;
	private Float total;
	private Integer quantity_sale;
	
	private Products product;
	
	@PrePersist
	public void prePersist() {
		this.created_at = LocalDateTime.now();
	}
}
