package com.moyeoba.project.token.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.Base64;

@Getter
public class JwtHeader {
    private final String alg = "HS256";             // 암호화 알고리즘
    private final String typ = "JWT";               // 토큰의 유형. jwt 고정

    public String toJson() {
        ObjectMapper mapper = new ObjectMapper();
        String headJson = null;
        try {
            headJson = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return headJson;
    }
    public String toJsonBase64() {
        String json = this.toJson();
        return Base64.getEncoder().encodeToString(json.getBytes());
    }

    public static JwtHeader getFromJson(String jsonStr) {
        JwtHeader header = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            header = mapper.readValue(jsonStr, JwtHeader.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return header;
    }
}
