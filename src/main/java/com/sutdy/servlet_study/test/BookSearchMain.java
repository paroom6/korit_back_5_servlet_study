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

public class BookSearchMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 검색할 도서명을 입력하세요 >>> 글
		// 도서명 / 저자 / 출판사
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		List<Book> bookList = new ArrayList<>();
		
		
		
		try {
			con = pool.getConnection();
			String sql ="SELECT \r\n"
					+ "	b.book_id,\r\n"
					+ "    b.book_name,\r\n"
					+ "    at.author_id,\r\n"
					+ "    at.author,\r\n"
					+ "    pt.publisher_id,\r\n"
					+ "    pt.publisher_name\r\n"
					+ "FROM 	\r\n"
					+ "	book b\r\n"
					+ "    left outer join author_tb at on(at.author_id = b.author_id)\r\n"
					+ "    left outer join publisher_tb pt on (pt.publisher_id = b.publisher_id);";
			pstmt = con.prepareStatement(sql); // sql창에 String sql 입력
			rs = pstmt.executeQuery(); //쿼리 실행 set자료형으로 획득
			while(rs.next()) {
				bookList.add(Book.builder()
								 .bookId(rs.getInt(1))
								 .bookName(rs.getNString(2))
								 .author(new Author(rs.getInt(3), rs.getNString(4)))
								 .publisher(new Publisher(rs.getInt(5),rs.getString(6)))
								 .build());
			}
			
		} catch (Exception e) { //throws를 Exception로 잡아놨기때문 (throw는 강제 예외생성)
			e.printStackTrace();
		} finally {// try catch 후 마지막에 실행
			pool.freeConnection(con, pstmt, rs);//서버와 연결을 끊기 위해 try문 밖에 변수 생성
		}
		Scanner scanner = new Scanner(System.in);
		while(true) {
			System.out.print("검색할 도서명을 입력하세요 >>> ");
			String findBook = scanner.nextLine();
			if("종료".equals(findBook)) {
				break;
			}
			for(int i = 0; i < bookList.size(); i++) {
				if(bookList.get(i).getBookName().indexOf(findBook) > -1) {
					System.out.println(bookList.get(i).getBookName() + " / " + bookList.get(i).getAuthor().getAuthorName() + " / " + bookList.get(i).getPublisher().getPublisherName());
				}
			}
		}
	}
}


