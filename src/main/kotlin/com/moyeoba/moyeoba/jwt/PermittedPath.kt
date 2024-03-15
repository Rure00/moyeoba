package com.moyeoba.moyeoba.jwt

object PermittedPath {
    private val OPEN_PATH = arrayOf("/test/*", "/user/*")

    fun isOpen(url: String): Boolean {
        for (path in OPEN_PATH) {
            var result = true

            val openUrl = if(path.split("/*").isNotEmpty()) {
                //println("split: ${path.split("/*")[0]}")
                path.split("/*")[0]

            } else {
                path
            }

            for (i in openUrl.indices) {
                if (openUrl[i] != url[i]) {
                    //println("${openUrl[i]} is different from ${url[i]}")
                    result = false
                    break
                }
            }

            if (result) return true
        }

        return false
    }
}