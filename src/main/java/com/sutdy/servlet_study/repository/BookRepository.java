package com.sutdy.servlet_study.repository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.sutdy.servlet_study.config.DBConnectionMgr;
import com.sutdy.servlet_study.entity.Author;
import com.sutdy.servlet_study.entity.Book;
import com.sutdy.servlet_study.entity.Publisher;


public class BookRepository {
	private static BookRepository instance;
	private DBConnectionMgr pool;

	
	private BookRepository() {
		pool = DBConnectionMgr.getInstance();
	}
	
	public static BookRepository getInstance() {
		if(instance == null) {
			instance = new BookRepository();
		}
		return instance;
	}
	
	public int saveBook(Book book) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = pool.getConnection();
			String sql = "insert into author_tb values (0, ?);";
			pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 
			pstmt.setString(1, book.getAuthor().getAuthorName());
			pstmt.executeUpdate();//insert, update, delete 사용시 필수 성공한 갯수만큼 리턴
			ResultSet rs =pstmt.getGeneratedKeys(); //자동증가된 키값 
			if(rs.next()) {
				book.getAuthor().setAuthorId(rs.getInt(1));
				}
			if(rs.next()) {
				book.setBookId(rs.getInt(1));
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		
		try {
			con = pool.getConnection();
			String sql = "insert into publisher_tb values (0, ?);";
			pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 
			pstmt.setString(1, book.getPublisher().getPublisherName());
			pstmt.executeUpdate();//insert, update, delete 사용시 필수 성공한 갯수만큼 리턴
			ResultSet rs =pstmt.getGeneratedKeys(); //자동증가된 키값 
			if(rs.next()) {
				book.getPublisher().setPublisherId(rs.getInt(1));
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		
		try {
			con = pool.getConnection();
			String sql = "insert into book values (0, ?, ?, ?)";
			pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 
			pstmt.setString(1, book.getBookName());
			pstmt.setInt(2, book.getAuthor().getAuthorId());
			pstmt.setInt(3, book.getPublisher().getPublisherId());
			pstmt.executeUpdate();
			ResultSet rs =pstmt.getGeneratedKeys(); 
			if(rs.next()) {
				book.setBookId(rs.getInt(1));
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		
		return 1;
	}
	
	public Book findBookByBookid(int bookId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Book findbook =null;
		
		try {
			con = pool.getConnection();
			String sql ="SELECT * FROM db_study.book_view where book_id = ?;";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bookId); 
			rs = pstmt.executeQuery(); 
			if (rs.next()) {//resultset의 경우 next()를 이용해 커서를 맞춰줘야한다.
			findbook = Book.builder()
					  	   .bookId(rs.getInt(1))
					  	   .bookName(rs.getString(2))
					  	   .author(Author.builder()
					  			   .authorId(rs.getInt(3))
					  			   .authorName(rs.getString(4))
					  			   .build())
					  	   .publisher(Publisher.builder()
					  			   .publisherId(rs.getInt(5))
					  			   .publisherName(rs.getString(6))
					  			   .build())
					  	   .build();		  	   
			}
		} catch (Exception e) { 
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}		
		return findbook;
	}
	
	public List<Book> searchBookList(Book book) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Book> bookList = new ArrayList<>();
		
		try {
			con = pool.getConnection();
			String sql ="SELECT * FROM db_study.book_view where ? = 1 \r\n";
			boolean c = false;
			if(book.getBookName() != null) {
				sql += "or book_name like '%" + book.getBookName() + "%'\r\n";
				c = true;
			} 
				
			if(book.getAuthor().getAuthorName() != null) {
				sql += "or author like '%" + book.getAuthor().getAuthorName() + "%'\r\n";
				c = true;
			} 
			
			if(book.getPublisher().getPublisherName() != null) {
				sql += "or publisher_name like '%" + book.getPublisher().getPublisherName() + "%'";
				c =true;
			} 
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, c ? 0 : 1);
			
			rs = pstmt.executeQuery(); 
			while (rs.next()) {//resultset의 경우 next()를 이용해 커서를 맞춰줘야한다.
				bookList.add(Book.builder()
					  	  .bookId(rs.getInt(1))
					  	  .bookName(rs.getString(2))
					  	  .author(Author.builder()
					  		   .authorId(rs.getInt(3))
					  		   .authorName(rs.getString(4))
					  		   .build())
					  	  .publisher(Publisher.builder()
					  		   .publisherId(rs.getInt(5))
					  		   .publisherName(rs.getString(6))
					  		   .build())
					  	  .build());		  	   
			}
		} catch (Exception e) { 
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}		
		return bookList;
	}
	public List<Book> searchBookList(Map<String, String> params) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Book> bookList = new ArrayList<>();
		Map<String, String> keyData = new HashMap<>();
		for(String key : params.keySet()) {
			String snakeKey = "";
			for(int i = 0; i < key.length(); i++) {
				if(Character.isUpperCase(key.charAt(i))) {
					snakeKey += "_" + Character.toLowerCase(key.charAt(i));
				} else {
					snakeKey += key.charAt(i);
				}
			}
			snakeKey.trim();
			keyData.put(key, snakeKey);
		}
		
		try {
			con = pool.getConnection();
			String sql ="SELECT * FROM db_study.book_view where ? = 1 ";
			
			for(String key : params.keySet()) {
				sql+= " or " + keyData.get(key) + " like ?";
			}
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, params.isEmpty() ? 1 : 0);
			int i = 2;
			for(String key : params.keySet()) {
				pstmt.setString(i, "%" + params.get(key) + "%");
				i++;
			}
			rs = pstmt.executeQuery(); 
			while (rs.next()) {//resultset의 경우 next()를 이용해 커서를 맞춰줘야한다.
				bookList.add(Book.builder()
					  	  .bookId(rs.getInt(1))
					  	  .bookName(rs.getString(2))
					  	  .author(Author.builder()
					  		   .authorId(rs.getInt(3))
					  		   .authorName(rs.getString(4))
					  		   .build())
					  	  .publisher(Publisher.builder()
					  		   .publisherId(rs.getInt(5))
					  		   .publisherName(rs.getString(6))
					  		   .build())
					  	  .build());		  	   
			}
		} catch (Exception e) { 
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}		
		return bookList;
	}
	
}
