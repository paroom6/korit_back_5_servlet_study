package com.sutdy.servlet_study.test;

import java.util.function.Consumer;

import com.sutdy.servlet_study.entity.Author;

class Print<T> implements Consumer<T> {
	public void accept(T t) {//추상메소드 오버라이드
		System.out.println(t);
	}
}

public class LambdaMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Consumer<Author> print0 = new Print<Author>();
		
		Consumer<Author> print1 = new Consumer<Author>() {
			
			@Override//추상메소드 익명클래스
			public void accept(Author t) {
				// TODO Auto-generated method stub
				System.out.println(t);
			}
		};
		
		Consumer<Author> print2 = Author -> System.out.println(Author);
		print0.accept(Author.builder().authorId(1).authorName("조성민").build());
		print1.accept(Author.builder().authorId(2).authorName("조성이").build());
		print2.accept(Author.builder().authorId(3).authorName("조성삼").build());
	forEach(print2);
	forEach(author -> {
		System.out.println("<<<test>>>");
		System.out.println(author);
	});
	
	}
	public static void forEach(Consumer<Author> action) {
		action.accept(Author.builder().authorId(4).authorName("조성사").build());
	}
}
