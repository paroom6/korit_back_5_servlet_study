package com.sutdy.servlet_study.service;

import com.sutdy.servlet_study.entity.Account;
import com.sutdy.servlet_study.repository.AccountRepository;

public class AccountService {
	
	private static AccountService instance;
	private AccountRepository accountRepository;
	
	private AccountService() {
		accountRepository = AccountRepository.getInstance();
	}

	public static AccountService getInstance() {
		if(instance == null) {
			instance = new AccountService();
		}
		return instance;
	}
	
	public int addAccount(Account account) {
		return accountRepository.saveAccount(account);
	}
	
	public Account getAccount(String username) {
		
		return accountRepository.findAccountByUsername(username);
	}
	
}
