package com.sutdy.servlet_study.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data

public class Account {
	private String username;
	private String passward;
	private String name;
	private String email;
}
