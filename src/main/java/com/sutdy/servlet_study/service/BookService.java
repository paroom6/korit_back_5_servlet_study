package com.sutdy.servlet_study.service;

import java.util.List;
import java.util.Map;

import com.sutdy.servlet_study.entity.Book;
import com.sutdy.servlet_study.repository.BookRepository;

public class BookService {
	private static BookService instance;
	private BookRepository bookRepository;

	private BookService() {
		bookRepository = BookRepository.getInstance();
	}
	
	public static BookService getInstance() {
		if(instance == null) {
			instance = new BookService();
		}
		return instance;
	}
	
	public boolean addBook(Book book) {
		return bookRepository.saveBook(book) > 0;
	}
	
	public Book getBook(int book_id) {
		return bookRepository.findBookByBookid(book_id);
	}
	
	public List<Book> getBookList(Book book) {
		return bookRepository.searchBookList(book);
	}
	public List<Book> getBookList(Map<String, String> params) {
		return bookRepository.searchBookList(params);
	}
}
