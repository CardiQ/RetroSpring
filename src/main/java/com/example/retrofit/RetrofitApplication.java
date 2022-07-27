package com.example.retrofit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.retrofit.mapper")
public class RetrofitApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetrofitApplication.class, args);
	}

}
