package com.sutdy.servlet_study.filter;

import java.io.IOException;
import java.net.http.HttpResponse;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")//요청주소를 포함하는 필터확인후 실행
public class ResponseCharactorEncodingFilter extends HttpFilter implements Filter {//전처리 필터 본문 메서드 후처리필터 순서
	
	
	public void destroy() {
		// TODO Auto-generated method stub
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {//http 이외의 다른 용도도 사용가능하기에 
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest httpRequest = (HttpServletRequest) request;//ResponseCharactorEncodingFilter 이므로 생략해도 좋다
		HttpServletResponse httpResponse = (HttpServletResponse) response;//http가 없다.다운캐스팅 하여 사용
		httpResponse.setCharacterEncoding("utf-8");
		//필터 전처리
		chain.doFilter(request, response);//요청주소의 선언된 메서드와 연결
		//필터 후처리
//		httpResponse.getWriter().println("무조건 후처리 함");
		
		
	}


	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
