package com.homesharing.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.Map;

public class JwtUtil {

    private static final String SECRET_KEY = System.getenv("SECRET_KEY"); // Đặt secret key của bạn ở đây
    private static final long EXPIRATION_TIME = 86400000; // Thời gian hết hạn của token (1 ngày)

    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
    private static final JWTVerifier verifier = JWT.require(algorithm).build();

    // Constructor private để ngăn tạo đối tượng của lớp
    private JwtUtil() {
        // Không làm gì cả
    }

    // Phương thức tạo token
    public static String generateToken(String username, String role, String email) {
        return JWT.create()
                .withSubject(username)
                .withClaim("role", role)     // Thêm trường "role"
                .withClaim("email", email)   // Thêm trường "email"
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);
    }

    // Phương thức để lấy tất cả các claims từ token đã xác minh
    public static Map<String, Claim> getAllClaims(String token) {
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getClaims(); // Lấy tất cả các claims dưới dạng Map
        } catch (JWTVerificationException e) {
            // Token không hợp lệ hoặc đã hết hạn
            return null;
        }
    }

}
