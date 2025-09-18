package exe201.studymatebackend.config;

import exe201.studymatebackend.pojo.Account;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${app.jwt.signer-key}")
    private String SIGNER_KEY;

    @Value("${app.jwt.expiration-ms}")
    private Long EXPIRATION_TIME;

    // hàm tạo chữ ký từ cái SIGNER_KEY
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SIGNER_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String generateToken(Account account) {
        return Jwts.builder()
                .setSubject(account.getUsername())

                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            String username = getUsernameFromToken(token);
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return (username.equals(userDetails.getUsername()));
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
