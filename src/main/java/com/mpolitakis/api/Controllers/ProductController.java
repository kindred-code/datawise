package com.mpolitakis.api.Controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mpolitakis.api.Models.Product;
import com.mpolitakis.api.Sec.services.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

	public static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	
	private final ProductService productService;

	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
	@GetMapping("/all")
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> list = productService.getAllProducts();
		return ResponseEntity.ok(list);

	}
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
	@PostMapping("/product")
	public ResponseEntity<Product> findProductByName(String name) throws ProductException {
		Product product = productService.findProductByName(name);
		if (product == null) {
			logger.error("Product with name {} not found.", name);
			throw new ProductException("Product with name " + name + " not found", HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(product);
	}

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Product>> findProductById(@PathVariable Long id) throws ProductException {
		Optional<Product> product = productService.findProductById(id);
		if (product == null) {
			logger.error("Product with id {} not found.", id);
			throw new ProductException("Product with id " + id + " not found", HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(product);
	}

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
	@PostMapping("/create")
	public ResponseEntity<Product> addProduct(@Validated @RequestBody Product product, Errors errors) throws ProductException {


		boolean productExists = productService.findProductByName(product.getName()) != null;
		if (productExists) {
			logger.error("Unable to create. A product with name {} already exist", product.getName());
			throw new ProductException("Unable to create. A product with name " + product.getName() + " already exist.",
					HttpStatus.CONFLICT);

		}

		productService.addProduct(product);
		
		return ResponseEntity.ok(product);
	}
    @PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable Long id, @Validated @RequestBody Product product, Errors errors)
			throws ProductException {
		if (errors.hasErrors()) {
			throw new ProductException(errors.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
		}
		
		Optional<Product> currentProduct = productService.findProductById(id);
		if (currentProduct == null) {
			logger.error("Unable to update. User with id {} not found.", id);
			throw new ProductException("Unable to update. Product with id " + id + " not found", HttpStatus.NOT_FOUND);
		}
		productService.updateProduct(id, product);

		return ResponseEntity.ok("The product has been updated");
	}
    @PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable Long id) throws ProductException {
		Optional<Product> currentProduct = productService.findProductById(id);
		if (currentProduct == null) {
			logger.error("Unable to delete. Product with id {} not found.", id);
			throw new ProductException("Unable to delete. Product with id " + id + " not found", HttpStatus.NOT_FOUND);
		}
		productService.deleteProduct(id);
		return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
	}
}
