package com.sutdy.servlet_study.filter;

import java.io.IOException;
import java.util.Arrays;

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


@WebFilter("/*")
public class RequestCharactorEncodingFilter extends HttpFilter implements Filter {

    public RequestCharactorEncodingFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
				HttpServletRequest httpRequest = (HttpServletRequest) request;//ResponseCharactorEncodingFilter 이므로 생략해도 좋다
				//HttpServletResponse httpResponse = (HttpServletResponse) response;//http가 없다.다운캐스팅 하여 사용
				String[] methods = new String[] {"POST","PUT"};
				
				//System.out.println(httpRequest.getMethod()); //대문자로 출력
				
				if(Arrays.asList(methods).contains(httpRequest.getMethod().toUpperCase())) {//리스트화된 methods가 요청때 들어온 메소드(post,put)를 포함하고 있으면   
					httpRequest.setCharacterEncoding("utf-8");//get과 delete는 굳이 request를 encoding 할 필요가 없기 때문에
				}
				
				//필터 전처리 영역
				chain.doFilter(request, response);//요청주소의 선언된 메서드와 연결 - 이 경우 post
				//필터 후처리 영역

	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
