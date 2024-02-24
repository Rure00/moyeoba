package com.moyeoba.project;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moyeoba.project.api.kakao.KakaoApiManager;
import com.moyeoba.project.api.kakao.KakaoProfileDto;
import com.moyeoba.project.api.kakao.KakaoTokenDto;
import com.moyeoba.project.service.KakaoService;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

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


	@Test
	void kakaoLogin() throws JsonProcessingException {
		String token = "2yaCfL9bE2GVLsRk3TgKuDSlbWJBFAMaJgsKPXNNAAABjdTjGIP-oZq-Jypvmw";

		RestTemplate profile_rt = new RestTemplate();
		HttpHeaders userDetailReqHeaders = new HttpHeaders();

		userDetailReqHeaders.add("Authorization", "Bearer " + token);
		userDetailReqHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
		HttpEntity<MultiValueMap<String, String>> naverProfileRequest = new HttpEntity<>(userDetailReqHeaders);

		ResponseEntity<String> userDetailResponse = profile_rt.exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				naverProfileRequest,
				String.class
		);
		System.out.println(userDetailResponse);

		ObjectMapper profile_om = new ObjectMapper();
		KakaoProfileDto kakaoProfile = null;
		try {
			kakaoProfile = profile_om.readValue(userDetailResponse.getBody(), KakaoProfileDto.class);
			System.out.println("Success: " + kakaoProfile.getId());
		} catch (JsonProcessingException je) {
			je.printStackTrace();
			throw je;
		}


	}

	@Test
	void splitTest() {
		String str = "abcde";
		String[] arr = str.split("k");

		for(String s: arr) {
			System.out.println(s);
		}
	}

}
