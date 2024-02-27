package com.moyeoba.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ProjectApplication {

	//refresh token 받고 새로운 token 만든 후 보내기
	//refresh token 을 쿠키에 넣기
	//endpoint 합치기) 인자로 처리

	/*

	 */

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}


}
