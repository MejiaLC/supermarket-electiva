package com.example.demo.SalesProducts.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.SalesProducts.domain.models.Products;
import com.example.demo.SalesProducts.domain.services.SalesService;

@RestController
@RequestMapping("/app")
public class SalesController {

	@Autowired
	private SalesService salesService;
	
	@PostMapping("/save")
	public ResponseEntity<?> saveProduct(@RequestBody Products products){
		salesService.addProduct(products);
		
		return new ResponseEntity<>("Producto agregado con exito", HttpStatus.OK);
	}
	
	@GetMapping("/list")
	public ResponseEntity<?> findAll(){		
		return new ResponseEntity<>(salesService.findAll(), HttpStatus.OK);
	}
}
