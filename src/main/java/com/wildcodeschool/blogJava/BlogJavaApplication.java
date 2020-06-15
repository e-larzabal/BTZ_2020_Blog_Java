package com.wildcodeschool.blogJava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogJavaApplication {

	private static Logger L = LoggerFactory.getLogger(BlogJavaApplication.class);

	public static void main(String[] args) {
		L.info("starting BlogJavaApplication..");
		SpringApplication.run(BlogJavaApplication.class, args);
	}

}
