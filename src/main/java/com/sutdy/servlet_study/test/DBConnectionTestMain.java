package com.sutdy.servlet_study.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.xdevapi.PreparableStatement;
import com.sutdy.servlet_study.config.DBConnectionMgr;
import com.sutdy.servlet_study.entity.Author;

public class DBConnectionTestMain {

	public static void main(String[] args)  {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			con = pool.getConnection();
			String sql ="select * from author_tb";
			pstmt = con.prepareStatement(sql); // sql창에 String sql 입력
			rs = pstmt.executeQuery(); //쿼리 실행 set자료형으로 획득
			List<Author> authorList = new ArrayList<>();
			while(rs.next()) {
				authorList.add(Author.builder()
									 .authorId(rs.getInt(1))
									 .authorName(rs.getNString(2))
									 .build()); //sql table의 데이터 타입에 맞추어서 작성
			}
			authorList.forEach(author -> System.out.println(author));//collection에 사용 map의 경우 entryset으로 바꿔 반복
//			for(Author author : authorList) {
//				System.out.println(author);
//			}
//			for(int i = 0; i < authorList.size(); i++) {
//				Author author2 = authorList.get(i);
//				System.out.println(author2);
//			}
		} catch (Exception e) { //throws를 Exception로 잡아놨기때문 (throw는 강제 예외생성)
			e.printStackTrace();
		} finally {// try catch 후 마지막에 실행
			pool.freeConnection(con, pstmt, rs);//서버와 연결을 끊기 위해 try문 밖에 변수 생성
		}
	
	}

}
