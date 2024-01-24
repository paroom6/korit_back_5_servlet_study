package com.sutdy.servlet_study.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Scalar;
import com.sutdy.servlet_study.config.DBConnectionMgr;
import com.sutdy.servlet_study.entity.Author;
import com.sutdy.servlet_study.entity.Book;
import com.sutdy.servlet_study.entity.Publisher;

public class BookSearchMain2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 검색할 도서명을 입력하세요 >>> 글
		// 도서명 / 저자 / 출판사
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		List<Book> bookList = new ArrayList<>();
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("검색할 도서명을 입력하세요 >>> ");
		String findBook = scanner.nextLine();
		
		try {
			con = pool.getConnection();
			String sql ="SELECT \r\n"
					+ "	b.book_id,\r\n"
					+ "    b.book_name,\r\n"
					+ "    at.author_id,\r\n"
					+ "    at.author,\r\n"
					+ "    pt.publisher_id,\r\n"
					+ "    pt.publisher_name\r\n"
					+ "FROM\r\n"
					+ "	   book b\r\n"
					+ "	   left outer join author_tb at on(at.author_id = b.author_id)\r\n"
					+ "    left outer join publisher_tb pt on (pt.publisher_id = b.publisher_id)\r\n"
					+ "where\r\n "
					+ "	   b.book_name like ?;";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + findBook + "%"); // 기본적으로 ''가 붙어서 들어간다.ㅋ
			rs = pstmt.executeQuery(); 
			while(rs.next()) {
				bookList.add(Book.builder()
								 .bookId(rs.getInt(1))
								 .bookName(rs.getNString(2))
								 .author(new Author(rs.getInt(3), rs.getNString(4)))
								 .publisher(new Publisher(rs.getInt(5),rs.getString(6)))
								 .build());
			}
			
			
		} catch (Exception e) { 
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		System.out.println("도서명 / 저자명 /출판사");
		
		bookList.forEach(book -> {
			System.out.println(book.getBookName() + " / " + book.getAuthor().getAuthorName() + " / " + book.getPublisher().getPublisherName());				
		});
		
	}
}


