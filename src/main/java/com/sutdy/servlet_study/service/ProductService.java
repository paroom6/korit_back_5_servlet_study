package com.sutdy.servlet_study.service;

import com.sutdy.servlet_study.entity.Product;
import com.sutdy.servlet_study.repository.ProductRepository;

public class ProductService {
	private static ProductService instance;
	private ProductRepository productRepository;
	
	private ProductService() {
		productRepository = ProductRepository.getInstance();
	}
	
	public static ProductService getInstance() {
		if(instance == null) {
			instance = new ProductService();
		}		
		return instance;
	}
	
	public int addProduct(Product product) {
		if(productRepository.productDoubleCheck(product) == 0) {
			return productRepository.productDoubleCheck(product);
		}
		return productRepository.saveProduct(product);
	}
	
	public Product getProduct(Product product) {
		return productRepository.findProduct(product);
	}
	
}
