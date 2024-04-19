package com.moyeoba.moyeoba.api.kakao

import javax.xml.crypto.Data



data class KakaoProfileDto(
        var id: Long,
        var connected_at: String,
        var kakao_account: KakaoAccount
) {
    constructor() : this(0L, "", KakaoAccount())

    //Filed Info Docs
    //https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#kakaoaccount

     data class KakaoAccount (
            var name: String,
            var email: String
    ) {
        constructor(): this("", "")
    }
}
