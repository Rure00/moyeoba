package com.moyeoba.moyeoba.api.naver

data class NaverProfileDto(
        var resultcode: String,
        var message: String,
        var response: Response
) {
        constructor(): this("", "", Response())

    data class Response(
            var id: String,
            var nickname: String,
            var name: String,
            var email: String,
            var gender: String,
            var age: String,
            var birthday: String,
            var profile_image: String,
            var birthyear: String,
            var mobile: String,
            var mobile_e164: String

    ) {
            constructor(): this(
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    ""
            )
    }
}
