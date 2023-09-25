package com.mpolitakis.api.Sec.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpolitakis.api.Models.Product;
import com.mpolitakis.api.repository.ProductRepository;



@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	
	@Override
	public List<Product> getAllProducts() {
		List<Product> products = new ArrayList<>();
		productRepository.findAll().forEach(products::add);
		return products;
	}

	
	@Override
	public Optional<Product> findProductById(Long id) {
		return productRepository.findById(id);
	}
	
	
	@Override
	public Product findProductByName(String name) {
		return productRepository.findByName(name);
	}

	
	@Override
	public void addProduct(Product product) {
		productRepository.save(product);
	}
	
	
	@Override
	public void updateProduct(Long id, Product product) {
		var productOld =findProductById(id);
		productOld.get().setName(product.getName());
		productOld.get().setPrice(product.getPrice());
	
		productRepository.save(productOld.get());
	}
	
	@Override
	public void deleteProduct(Long id) {
		
		productRepository.deleteById(id);
	}

}
