package com.moyeoba.project;

import com.moyeoba.project.token.TokenManager;
import com.moyeoba.project.token.TokenManagerImpl;
import com.moyeoba.project.token.data.TokenPair;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
class ProjectApplicationTests {

	@Autowired
	TokenManagerImpl tokenManager;

	//TokenManager 테스트
	@Test
	void tokenTest() {
		TokenPair tokenPair = tokenManager.generateTokens(12L);
		System.out.println("토큰 생성 완료: " + tokenPair.getAccessToken());

		boolean isValidated = tokenManager.validateToken(tokenPair.getAccessToken());
		System.out.println("토큰 인증 확인: " + isValidated);
	}

	@Test
	void checkIsOpen() {
		boolean result = true;
		String url = "/login/naver";
		String[] OPEN_PATH = new String[] {
				"/test/*", "/login/*"
		};

		for(String path: OPEN_PATH) {
			result = true;
			String openUrl = path.split("/\\*")[0];
			System.out.println(openUrl);

			for(int i =0; i < openUrl.length(); i++) {
				if(openUrl.charAt(i) != url.charAt(i)) {
					System.out.println("Not Matched Char at " + i + ", " + openUrl.charAt(i) +", " + url.charAt(i));
					result = false;
					break;
				}
			}

			if(result) break;
		}

		System.out.println("result: " + result);
	}

}
