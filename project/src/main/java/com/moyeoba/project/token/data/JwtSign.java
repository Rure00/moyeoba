package com.moyeoba.project.token.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Getter
@NoArgsConstructor
public class JwtSign {
    private String verification;

    private String secretKey;

    public JwtSign(@NotNull JwtHeader header, @NotNull JwtPayload payload, @NotNull String secretKey) {
        String sum = header.toJsonBase64() + "." + payload.toJsonBase64();
        verification =  Base64.getEncoder().encodeToString(sum.getBytes());
        this.secretKey = secretKey;
    }
    public String toJsonBase64() {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        assert json != null;
        return Base64.getEncoder().encodeToString(json.getBytes());
    }
    public boolean checkToken(JwtHeader header, JwtPayload payload) {
        String sum = header.toJsonBase64() + "." + payload.toJsonBase64();
        String vrf =  Base64.getEncoder().encodeToString(sum.getBytes());

        return vrf.equals(verification);
    }

    public static JwtSign getFromJson(String jsonStr) {
        JwtSign sign = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            sign = mapper.readValue(jsonStr, JwtSign.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sign;
    }
}
