package com.mpolitakis.datawise.Sec.services;

import java.util.List;
import java.util.Optional;

import com.mpolitakis.datawise.Models.Product;



public interface ProductService {

	List<Product> getAllProducts();

	Optional<Product> findProductById(Long id);
	
	Product findProductByName(String name);

	void addProduct(Product product);

	void updateProduct(Long id, Product product);
	
	void deleteProduct(Long id);
    
}
