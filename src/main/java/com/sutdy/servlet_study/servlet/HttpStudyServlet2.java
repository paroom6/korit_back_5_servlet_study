package com.sutdy.servlet_study.servlet;

import java.io.IOException;
import java.text.CollationKey;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sutdy.servlet_study.utils.ParamsConverter;

import lombok.Value;
import lombok.val;


@WebServlet("/http1")//주소명은 소문자로
public class HttpStudyServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public HttpStudyServlet2() {
        super();
        
    }

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { //client가 백엔드에 요청한 목록 리스트
    	Map<String, String[]> map = request.getParameterMap(); //키를 명시하지 않고 한 덩어리로 받기 좋다.
    	Map<String, String> paramsMap = ParamsConverter.convertParamsMapToMap(map);
    		
    	SortedMap<String, String[]> sMap = Collections.synchronizedSortedMap(new TreeMap<String, String[]>(request.getParameterMap()));
    	synchronized (sMap) {
    		for(String key : sMap.keySet()) {
    			String[] value = sMap.get(key);
    			for(int i = 0; i < value.length; i++) {
    				System.out.println(key + ": " + value[i]);
    			} 
    		}
		}
    	System.out.println(paramsMap);
    	System.out.println(request.getParameter("name"));
    	
//    	Map<String, String> paramsMap2 = new HashMap<>(); 
//    	Iterator<String> ir =  request.getParameterNames().asIterator();//키만 넣어둔것
//    	while(ir.hasNext()) {
//    		String key = ir.next();
//    		paramsMap2.put(key, request.getParameter(key));//key = 맵의 키들
//    		//System.out.println(ir.next());
//    	}
//    	
//    	System.out.println(paramsMap2.toString());
    	
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
