package com.moyeoba.project.token.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

@Getter
public class JwtPayload {
    private final Integer VALID_TIME;     //분
    private final String exp;     //expiration
    private final String uid;     //user id

    public JwtPayload(Long id, Integer validTime) {
        VALID_TIME = validTime;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, VALID_TIME);
        Date expiration = new Date(calendar.getTimeInMillis());

        uid = Long.toString(id);
        exp = Long.toString(expiration.getTime());
    }

    public String toJson() {
        ObjectMapper mapper = new ObjectMapper();
        String payloadJson = null;
        try {
            payloadJson = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return payloadJson;
    }
    public String toJsonBase64() {
        String json = this.toJson();
        return Base64.getEncoder().encodeToString(json.getBytes());
    }

    public static JwtPayload getFromJson(String jsonStr) {
        JwtPayload payload = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            payload = mapper.readValue(jsonStr, JwtPayload.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return payload;
    }
}
