package com.sutdy.servlet_study.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sutdy.servlet_study.entity.Author;
import com.sutdy.servlet_study.entity.Book;
import com.sutdy.servlet_study.entity.Publisher;
import com.sutdy.servlet_study.service.BookService;


@WebServlet("/Books")
public class BookListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookService bookService;
	
    public BookListServlet() {
        super();
        bookService = BookService.getInstance();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//params :무엇이 들어와도 조회가능하게  2가지 이상 &&조건  
		//bookName -> like 조회
		//authorName -> like 조회
		//publisherName -> like 조회
		Map<String, String> params = new HashMap<>();
		String bookName = request.getParameter("bookName");//String
		String authorName = request.getParameter("authorName");
		String publisherName = request.getParameter("publisherName");
		if(request.getParameter("bookName") != null) {
			params.put("bookName", bookName);
		}
		if(request.getParameter("authorName") != null) {
			params.put("authorName", authorName);
		}
		if(request.getParameter("publisherName") != null) {
			params.put("publisherName", publisherName);
		}
		Book book = Book.builder()
				.bookName(bookName)
				.author(Author.builder()
						.authorName(authorName)
						.build())
				.publisher(Publisher.builder()
						.publisherName(publisherName)
						.build())
				.build();
		List<Book> findBooks = bookService.getBookList(book);
		List<Book> findBookList = bookService.getBookList(params);
		response.setStatus(200);// 200 201 203 301 302 400 401 403권한 404 경로 405 메소드부재 415 호환되지 않는 타입 423 (500:서버측 문제) 
		findBookList.forEach(findBook -> {
			try {
				response.getWriter().println(findBook);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
	}

	

}
