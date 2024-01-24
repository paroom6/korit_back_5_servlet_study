 package com.sutdy.servlet_study.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sutdy.servlet_study.entity.Product;
import com.sutdy.servlet_study.service.ProductService;


@WebServlet("/product")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductService productService;

	
    public ProductServlet() {
        
        // TODO Auto-generated constructor stub
        productService = ProductService.getInstance();
    }

    //1. 키워드 인식 후 키워드에 맞추어 조회하기 완료
    //2. price int화 필요한가? 비교과정에서 valueOf 메서드가 throw NumberFormatException를 발생할 수도 있음 try catch 필요할지도.
    //3. 중복방지 완료 : saveProduct에 중복방지 기능이 필요한가?
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strPrice = request.getParameter("price");//String
		int price = 0;
		try {
			if(Integer.valueOf(strPrice) != null) {
				price = Integer.valueOf(strPrice);
			}
		} catch (NumberFormatException e) {
			// TODO: handle exception
			response.setStatus(400);
			response.getWriter().println("숫자만 입력해야 합니다.");
			return;
		}
		
		Product product = Product.builder()
				.productName(request.getParameter("productName"))
				.price(price)
				.size(request.getParameter("size"))
				.color(request.getParameter("color"))
				.build();
		Product findProduct = productService.getProduct(product);
		response.setStatus(200);// 200 201 203 301 302 400 401 403권한 404 경로 405 메소드부재 415 호환되지 않는 타입 423 (500:서버측 문제) 
		response.getWriter().println(findProduct);
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strPrice = request.getParameter("price");//String
		int price = 0;
		try {
			if(Integer.valueOf(strPrice) != null) {
				price = Integer.valueOf(strPrice);
			}
		} catch (Exception e) {
			// TODO: handle exception
			//response.getWriter().println("잘못 입력하셨습니다.");
			//e.printStackTrace();
			response.setStatus(400);
			response.getWriter().println("숫자만 입력해야 합니다.");
			return;
		}
		
		
		Product product = Product.builder()
				.productName(request.getParameter("productName"))
				.price(price)
				.size(request.getParameter("size"))
				.color(request.getParameter("color"))
				.build();
//		if(productService.getProduct(product.getProductName()) != null) { get을 product로 받았음 
//			response.setStatus(400); //이미 만든 상품명확인 코드를 재활용, //가독성좋게 메서드명을 바꾸거나 메서드의 기능을 나누어라 
//			response.getWriter().println("이미 등록된 상품명입니다.");
//			return;
//		}
		int body = productService.addProduct(product);
		if(body == 0) {
			response.setStatus(400);
			response.getWriter().println("이미 등록된 상품명입니다.");
			return;
		} 
		response.setStatus(201);				
		response.getWriter().println(body);
	}

}
