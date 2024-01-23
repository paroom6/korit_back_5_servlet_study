package com.sutdy.servlet_study.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data

public class Author {
	private int authorId;
	private String authorName;
	public Author(int authorId, String authorName) {
		this.authorId = authorId;
		this.authorName = authorName;
	}
}
