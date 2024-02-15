package com.moyeoba.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

	/* TODO: STEP!
	    https://radpro.tistory.com/675
		1. 네이버 로그인 요청받기	https://developers.naver.com/docs/login/devguide/devguide.md#3-4-1-%EB%84%A4%EC%9D%B4%EB%B2%84-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EC%97%B0%EB%8F%99%EC%9D%84-%EA%B0%9C%EB%B0%9C%ED%95%98%EA%B8%B0%EC%97%90-%EC%95%9E%EC%84%9C
			-> 네이버 로그인 연동 url 생성
			-> callback 에서 code 확인
			-> code 인증 시 토근 요청
			-> 토큰 반환
		2. 자체 토큰 생성	https://velog.io/@hiy7030/Spring-Security-JWT-%EC%83%9D%EC%84%B1
			-> access token 및 refresh token 고려
		3. User 및 Token DB 생성
			-> Token Black List 로직 작성


	 */

}
