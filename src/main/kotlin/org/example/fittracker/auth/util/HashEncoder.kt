package org.example.fittracker.auth.util

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class HashEncoder {

    private val bcrypt = BCryptPasswordEncoder()

    fun encode(raw: String): String = bcrypt.encode(raw).toString()

    fun matches(raw: String, password: String): Boolean = bcrypt.matches(raw, password)
}