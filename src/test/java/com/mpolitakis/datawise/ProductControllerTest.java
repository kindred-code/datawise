package com.mpolitakis.datawise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

import com.mpolitakis.datawise.Controllers.ProductController;
import com.mpolitakis.datawise.Controllers.ProductException;
import com.mpolitakis.datawise.Models.Product;
import com.mpolitakis.datawise.Sec.services.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Mock
    private Errors errors;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllProducts() {
        List<Product> productList = new ArrayList<Product>();
        when(productService.getAllProducts()).thenReturn(productList);

        ResponseEntity<List<Product>> response = productController.getAllProducts();

        verify(productService, times(1)).getAllProducts();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productList, response.getBody());
    }

    @Test
    public void testFindProductByName() throws ProductException {
        String productName = "ExampleProduct";
        Product product = new Product(productName, 2);
        when(productService.findProductByName(productName)).thenReturn(product);

        ResponseEntity<Product> response = productController.findProductByName(productName);

        verify(productService, times(1)).findProductByName(productName);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    public void testFindProductById() throws ProductException {
        Long productId = 1L;
        Optional<Product> product = Optional.of(new Product(productId));
        when(productService.findProductById(productId)).thenReturn(product);

        ResponseEntity<Optional<Product>> response = productController.findProductById(productId);

        verify(productService, times(1)).findProductById(productId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    public void testAddProduct() throws ProductException {
        Product newProduct = new Product("ExampleProduct", 2);
        when(productService.findProductByName(newProduct.getName())).thenReturn(null);

        ResponseEntity<Product> response = productController.addProduct(newProduct, errors);

        verify(productService, times(1)).findProductByName(newProduct.getName());
        verify(productService, times(1)).addProduct(newProduct);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newProduct, response.getBody());
    }

    @Test
    public void testUpdateProduct() throws ProductException {
        Long productId = 1L;
        Product updatedProduct = new Product("ExampleProduct1", 3);
        Optional<Product> currentProduct = Optional.of(new Product("ExampleProduct", 2));
        when(productService.findProductById(productId)).thenReturn(currentProduct);

        ResponseEntity<?> response = productController.updateProduct(productId, updatedProduct, errors);

        verify(productService, times(1)).findProductById(productId);
        verify(productService, times(1)).updateProduct(productId, updatedProduct);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("The product has been updated", response.getBody());
    }

    @Test
    public void testDeleteProduct() throws ProductException {
        Long productId = 1L;
        Optional<Product> currentProduct = Optional.of(new Product(productId));
        when(productService.findProductById(productId)).thenReturn(currentProduct);

        ResponseEntity<?> response = productController.deleteProduct(productId);

        verify(productService, times(1)).findProductById(productId);
        verify(productService, times(1)).deleteProduct(productId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }


}

