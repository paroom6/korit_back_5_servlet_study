package com.sutdy.servlet_study.repository;

import java.util.ArrayList;
import java.util.List;
import com.sutdy.servlet_study.entity.Product;

public class ProductRepository {
	private static ProductRepository instance;
	private List<Product> productList; 
	
	private ProductRepository() {
		productList = new ArrayList<>();
	}
	
	
	public static ProductRepository getInstance() {
		if(instance == null) {
			instance = new ProductRepository();
		}		
		return instance;
	}
	public int productDoubleCheck(Product product) {
		for(Product p : productList) {//중복체크
			if(p.getProductName().equals(product.getProductName())) {
				return 0;
			}
		}
		return 1;
	}
	
	public int saveProduct(Product product) {
		
		productList.add(product);
		return 1;
	}
	
	public Product findProduct(Product findproduct) {
		
		for(Product product : productList) {
			if(product.getProductName().equalsIgnoreCase(findproduct.getProductName())) {
				return product;
			}
		}
		
		for(Product product : productList) {
			if(product.getPrice() == findproduct.getPrice()) {
				return product;
			}
		}
		for(Product product : productList) {
			if(product.getSize().equalsIgnoreCase(findproduct.getSize())) {
				return product;
			}
		}
		for(Product product : productList) {
			if(product.getColor().equalsIgnoreCase(findproduct.getColor())) {
				return product;
			}
		}
		
		return null;
	}
	
}
