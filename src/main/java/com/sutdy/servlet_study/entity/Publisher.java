package com.sutdy.servlet_study.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data


public class Publisher {
	private int publisherId;
	private String  publisherName;
	public Publisher(int publisherId, String publisherName) {
		this.publisherId = publisherId;
		this.publisherName = publisherName;
	}
}
