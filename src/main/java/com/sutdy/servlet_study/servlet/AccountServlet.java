package com.sutdy.servlet_study.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sutdy.servlet_study.entity.Account;
import com.sutdy.servlet_study.service.AccountService;

@WebServlet("/account")
public class AccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AccountService accountService;
	
    public AccountServlet() {
        super();
        accountService = AccountService.getInstance();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username =request.getParameter("username");
		Account account = accountService.getAccount(username);
		response.setStatus(200);
		response.getWriter().println(account);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String passward = request.getParameter("passward");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		
		Account  account = Account.builder()
				.username(username)
				.passward(passward)
				.name(name)
				.email(email)
				.build();
		
		int body = accountService.addAccount(account);
		response.setStatus(201);
		response.getWriter().println(body);
	}

}
