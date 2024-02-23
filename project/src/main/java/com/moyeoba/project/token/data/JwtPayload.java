package com.moyeoba.project.token.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

@Getter
@NoArgsConstructor
public class JwtPayload {
    private Integer validTime;     //분
    private String exp;     //expiration
    private String uid;     //user id

    public JwtPayload(Long id, Integer validTime) {
        this.validTime = validTime;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, validTime);
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
