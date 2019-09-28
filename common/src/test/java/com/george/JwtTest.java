package com.george;

import com.google.common.collect.Maps;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author GeorgeChan 2019/9/28 15:24
 * @version 1.0
 * @since jdk1.8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@SpringBootConfiguration
public class JwtTest {
    @Test
    public void test() {
        System.out.println(123);
    }

    /**
     * 创建jwt token
     */
    @Test
    public void createJwtTest() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("role", new String[]{"admin","root","user"});
        map.put("resource","login");
        JwtBuilder builder = Jwts.builder().setId("123")
                .setSubject("小智")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "george")
                .setExpiration(new Date(new Date().getTime() + 60000))
                .claim("role", "admin")
                .addClaims(map);
        // 打印taken
        System.out.println(builder.compact());
    }

    /**
     * 解析token
     */
    @Test
    public void parseJwt() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMiLCJzdWIiOiLlsI_mmboiLCJpYXQiOjE1Njk2NTYzNDIsImV4cCI6MTU2OTY1NjQwMiwicm9sZSI6WyJhZG1pbiIsInJvb3QiLCJ1c2VyIl0sInJlc291cmNlIjoibG9naW4ifQ.mjnZvhJiDgCzL9OGHEieLy5bjsxpYThf6JUBFZmmwCg";
        // 解析token
        Claims claims = Jwts.parser().setSigningKey("george").parseClaimsJws(token).getBody();
        System.out.println("id: " + claims.getId());
        System.out.println("登录人： " + claims.getSubject());
        System.out.println("登录时间： " + claims.getIssuedAt());
        System.out.println("过期时间： "+claims.getExpiration());
        System.out.println("当前角色： " + claims.get("role"));
    }
}
