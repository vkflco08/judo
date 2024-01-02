package com.example.judo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JudoApplication

fun main(args: Array<String>) {
	runApplication<JudoApplication>(*args)
}
