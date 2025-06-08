package com.master.springsecondproject.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.Base64
import java.util.Date

@Service
class JwtService(
    @Value("\${jwt.secret}") private val jwtSecret: String
) {
    private val secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret))
    private val accessTokenValidate = 15L * 60L * 60L * 1000L
    private val refreshTokenValidate = 30L * 24L * 60L * 60L * 1000L

    fun generateToken(
        userId: String,
        type: String,
        expiry: Long
    ): String {
        val now = Date()
        val expiryDate = Date(now.time + expiry)
        return Jwts.builder()
            .subject(userId)
            .claim("type", type)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(secretKey, Jwts.SIG.HS256)
            .compact()
    }

    fun generateAccessToken(userId: String): String =
        generateToken(userId = userId, type = "access", expiry = accessTokenValidate)

    fun generateRefreshToken(userId: String): String =
        generateToken(userId = userId, type = "refresh", expiry = refreshTokenValidate)

    fun validateAccessToken(token: String): Boolean {
        val claims = getClaimsFromToken(token) ?: return false
        val tokenType = claims["type"] as? String ?: return false
        return tokenType == "access"
    }

    fun validateRefreshToken(token: String): Boolean {
        val claims = getClaimsFromToken(token = token) ?: return false
        val tokenType = claims["type"] as? String ?: return false
        return tokenType == "refresh"
    }

    fun getUserIdByToken(token: String): String {
        val claims = getClaimsFromToken(token = token) ?: throw ResponseStatusException(
            HttpStatusCode.valueOf(401),
            "Invalid token"
        )
        return claims.subject
    }

    fun getClaimsFromToken(token: String): Claims? {
        val rawToken = if (token.startsWith("Bearer ")) {
            token.removePrefix("Bearer ")
        } else token
        return try {
            Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(rawToken)
                .payload
        } catch (e: Exception) {
            null
        }
    }

}