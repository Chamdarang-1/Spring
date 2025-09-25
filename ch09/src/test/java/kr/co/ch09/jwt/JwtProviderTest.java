package kr.co.ch09.jwt;

import io.jsonwebtoken.Claims;
import kr.co.ch09.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtProviderTest {

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    void createToken() {
        User user = User.builder()
                .usid("a101")
                .pass("1234")
                .name("홍길동")
                .role("ADMIN")
                .age(23)
                .build();

        String token = jwtProvider.createToken(user, -1);

        System.out.println(token);

    }

    @Test
    void getClaims() {

        String token ="eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJqb3NlcGg4MDM0QG5hdmVyLmNvbSIsImlhdCI6MTc1ODc2Mjg5MSwiZXhwIjoxNzU4ODQ5MjkxLCJ1c2VybmFtZSI6ImExMDEiLCJyb2xlIjoiQURNSU4ifQ.YUi-a46G4ZSoi0KBJasRai87NAoCNzvtpHgXwEebx6Y";

        Claims claims = jwtProvider.getClaims(token);
        System.out.println(claims);
    }

    @Test
    void getAuthentication() {

        String token ="eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJqb3NlcGg4MDM0QG5hdmVyLmNvbSIsImlhdCI6MTc1ODc2Mjg5MSwiZXhwIjoxNzU4ODQ5MjkxLCJ1c2VybmFtZSI6ImExMDEiLCJyb2xlIjoiQURNSU4ifQ.YUi-a46G4ZSoi0KBJasRai87NAoCNzvtpHgXwEebx6Y";

        Authentication authentication = jwtProvider.getAuthentication(token);
        User user = (User) authentication.getPrincipal();

        System.out.println(user);
    }

    @Test
    void validateToken() {

        String token ="eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJqb3NlcGg4MDM0QG5hdmVyLmNvbSIsImlhdCI6MTc1ODc2Mjg5MSwiZXhwIjoxNzU4ODQ5MjkxLCJ1c2VybmFtZSI6ImExMDEiLCJyb2xlIjoiQURNSU4ifQ.YUi-a46G4ZSoi0KBJasRai87NAoCNzvtpHgXwEebx6Y";
        String expiredToken = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJqb3NlcGg4MDM0QG5hdmVyLmNvbSIsImlhdCI6MTc1ODc2MzYwNywiZXhwIjoxNzU4Njc3MjA3LCJ1c2VybmFtZSI6ImExMDEiLCJyb2xlIjoiQURNSU4ifQ.pT7HuYMwi0oJuk97AIefzbM-VL0-m6Qm0a7BHfp8gmE";

        try{
            jwtProvider.validateToken(token);
            System.out.println("토큰 이상 없음");

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Test
    void getIssuer() {
    }

    @Test
    void getSecretKey() {
    }
}