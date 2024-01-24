package com.sutdy.servlet_study.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sutdy.servlet_study.utils.ParamsConverter;

import lombok.Value;
import lombok.val;


@WebServlet("/http")//주소명은 소문자로
public class HttpStudyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public HttpStudyServlet() {
        super();
        
    }
    //http메소드
    /*
     * POST 요청 	: C reate 추가 //GET과 차이, 왜 getParameterMap()은 value로 Sting[]인가? 같은 키의 다양한 값을 받을 수 있다.
     * GET 요청 	: R ead 조회  //GET요청 이외에는 body 사용
     * PUSH 요청 	: U pdate 수정 
     * DELETE 요청	: D elete 삭제
     * 요청메세지는 같으나 메소드에 따라 발생하는 동작이 달라진다.
     * 
     * html: 서버를 구성하는 코드
     * 
     * CSR 클라이언트 사이드 렌더링
     * :react
     * 클라이언트에서 html 을 작성 필요한 데이터를 서버에 요청 데이터를 응답받음
     * 
     * SSR 서버 사이드 렌더링
     * : 서블릿 JSP 
     * 클라이언트에서 요청을 하면 서버에서 html을 완성 후 html로 응답
     */
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { //client가 백엔드에 요청한 목록 리스트
    	//한글깨짐 - body로 request를 보낼때 utf-8로 변환 필요 - 필터처리
    	//request.setCharacterEncoding("utf-8");
    	Map<String, String[]> map = request.getParameterMap(); //키를 명시하지 않고 한 덩어리로 받기 좋다.
    	Map<String, String> paramsMap = ParamsConverter.convertParamsMapToMap(map);

    	System.out.println(paramsMap);
    	System.out.println(request.getParameter("name"));
    	
    	Map<String, String> paramsMap2 = new HashMap<>(); 
    	Iterator<String> ir =  request.getParameterNames().asIterator();//키만 넣어둔것
    	while(ir.hasNext()) {
    		String key = ir.next();
    		paramsMap2.put(key, request.getParameter(key));//key = 맵의 키들
    		//System.out.println(ir.next());
    	}
    	// request.getParameterValues();//값만 넣어둔것
    	
    	//String nameParams = request.getParameter("name"); //요청을 받는 자료형은 Sting 이다. 한글의 경우 마지막 글자가 씹힐 수도 있으니 한번 더 확인
	
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {//주소창에 다 떠서 로그인의 경우 post로 처리한다. 웹상 로그인된 사용자의 추가로 본다.
		Map<String, String[]> map = request.getParameterMap();
		Map<String, String> paramsMap = ParamsConverter.convertParamsMapToMap(map);
    	System.out.println(paramsMap);
	}
	
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

}
