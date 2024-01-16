package com.sutdy.servlet_study.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet("/hello")//요청주소 먼저 확인
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {//웹은 무조건 요청과 응답에 의해 이루어 진다.
			// TODO Auto-generated method stub
			System.out.println(request.getMethod());
			System.out.println(request.getRequestURL());//전체경로를 출력
			System.out.println(request.getRequestURI());//서버 주소를 제외한 서버주소를 출력
			System.out.println(response.getStatus());//서버에서 돌아온 응답
			
			response.setContentType("text/plain");//응답할 코드의 데이터 타입 지정// 나중에는 Json이 다수
			//response.setCharacterEncoding("UTF-8"); //한글을 사용하면 항상 인코딩해야함 + 응답되기 전 필요 모든메소드에서 사용하기위해 filter 사용
			System.out.println(response.getContentType());
			response.getWriter().println("헬로");//응답을 줄 때 사용되는 코드
			
			System.out.println("요청이 들어옴!!!");
			
			
		}

}
