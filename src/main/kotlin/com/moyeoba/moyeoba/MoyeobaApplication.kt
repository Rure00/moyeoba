package com.moyeoba.moyeoba

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.servers.Server
import io.swagger.v3.oas.annotations.servers.ServerVariable
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MoyeobaApplication

fun main(args: Array<String>) {
	runApplication<MoyeobaApplication>(*args)
}
