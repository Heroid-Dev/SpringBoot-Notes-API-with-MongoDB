package com.master.springsecondproject.security


import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.stereotype.Component

@Component
class HashEncoded {

    val argon = Argon2PasswordEncoder(
        16,
        32,
        1,
        65536,
        3
    )

    fun encode(raw: String): String = argon.encode(raw)
    fun matches(raw: String, hashed: String): Boolean = argon.matches(raw, hashed)

}