package com.moyeoba.project.api.kakao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DateTimeException;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoProfileDto {
    private String id;
    private String connected_at;
    private kakao_account kakao_account;

    //Filed Info Docs
    //https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#kakaoaccount
    @Data
    public class kakao_account {
        private String name;
        private String email;
    }
}
