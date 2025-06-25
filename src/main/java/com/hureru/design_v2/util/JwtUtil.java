package com.hureru.design_v2.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT Token 工具类
 * * 负责：
 * 1. 从用户信息生成Token
 * 2. 从Token中解析用户信息
 * 3. 验证Token的有效性
 */
@Component
public class JwtUtil {

    // 从application.yml或application.properties文件中读取密钥
    // 建议使用更安全的方式管理，例如环境变量或配置中心
    @Value("${jwt.secret}")
    private String secret;

    // 从配置文件中读取Token的过期时间（单位：秒）
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 根据UserDetails生成JWT Token
     *
     * @param userDetails 用户信息
     * @return Token字符串
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        // 你可以在这里存放更多自定义的信息到claims中
        // claims.put("role", userDetails.getAuthorities());
        return doGenerateToken(claims, userDetails.getUsername());
    }

    /**
     * 从Token中获取用户名
     *
     * @param token JWT Token
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * 验证Token是否有效
     * 1. 验证用户名是否一致
     * 2. 验证Token是否已过期
     *
     * @param token       JWT Token
     * @param userDetails 从数据库中查询出的用户信息
     * @return 是否有效
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    // --- 私有辅助方法 ---

    /**
     * Token生成的核心方法
     *
     * @param claims  自定义的声明
     * @param subject 用户名
     * @return Token字符串
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        final Date createdDate = new Date();
        final Date expirationDate = calculateExpirationDate(createdDate);

        // 获取用于签名的密钥对象
        SecretKey secretKey = getSigningKey();

        return Jwts.builder()
                .setClaims(claims)           // 设置自定义声明
                .setSubject(subject)         // 设置主题（通常是用户名）
                .setIssuedAt(createdDate)    // 设置签发时间
                .setExpiration(expirationDate) // 设置过期时间
                .signWith(secretKey)         // 使用密钥和指定的签名算法进行签名
                .compact();                  // 生成并返回最终的Token字符串
    }

    /**
     * 检查Token是否已过期
     *
     * @param token JWT Token
     * @return 如果已过期返回true，否则返回false
     */
    private boolean isTokenExpired(String token) {
        final Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    /**
     * 从Token中获取过期时间
     *
     * @param token JWT Token
     * @return 过期时间
     */
    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * 从Token中获取指定的Claim（声明）
     *
     * @param token          JWT Token
     * @param claimsResolver 一个函数，用于从Claims中提取特定的值
     * @param <T>            Claim值的类型
     * @return Claim值
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 从Token中解析出所有的Claims（声明）
     *
     * @param token JWT Token
     * @return Claims对象
     */
    private Claims getAllClaimsFromToken(String token) {
        // 获取用于解析的密钥对象
        SecretKey secretKey = getSigningKey();

        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 根据配置的过期时间计算具体的过期日期
     *
     * @param createdDate 签发日期
     * @return 过期日期
     */
    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + expiration * 1000); // 配置文件中的单位是秒
    }

    /**
     * 根据配置的secret字符串生成一个安全的SecretKey对象，用于签名和验证
     *
     * @return SecretKey对象
     */
    private SecretKey getSigningKey() {
        // 将String类型的密钥转换为字节数组
        byte[] keyBytes = this.secret.getBytes(StandardCharsets.UTF_8);
        // 使用HMAC-SHA算法生成一个SecretKey
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
