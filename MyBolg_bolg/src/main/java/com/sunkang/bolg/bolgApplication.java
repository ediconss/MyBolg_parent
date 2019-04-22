package com.sunkang.bolg;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.sunkang.bolg.util.*;
@SpringBootApplication
public class bolgApplication {

	public static void main(String[] args) {
		SpringApplication.run( bolgApplication.class, args);
	}

	@Bean
	public JwtUtil jwtUtil(){
		return new  JwtUtil();
	}

	@Bean
	public FreemarkerUtil freemarkerUtil(){
		return new  FreemarkerUtil();
	}
}
