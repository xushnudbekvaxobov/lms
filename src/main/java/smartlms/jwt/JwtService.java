package smartlms.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.yaml.snakeyaml.tokens.AliasToken;
import smartlms.dto.response.JwtResponseDto;
import smartlms.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {
    @Value("${jwt.secret.key}")
    private String secretKey;
    @Value("${jwt.access.expiration.time}")
    private long accessTokenExpiration;
    @Value("${jwt.refresh.expiration.time}")
    private long refreshTokenExpiration;


    public String generateAccessToken(UserEntity userEntity) {
        return Jwts
                .builder()
                .subject(userEntity.getUsername())
                .claim("role", "ROLE_" + userEntity.getRole())
                .claim("id", userEntity.getId())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(getSignKey())
                .compact();
    }
    public String generateRefreshToken(UserEntity userEntity) {
        return Jwts
                .builder()
                .subject(userEntity.getUsername())
                .claim("role",  "ROLE_" + userEntity.getRole())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(getSignKey())
                .compact();
    }

    public Boolean isValid(String refreshToken) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(refreshToken)
                .getPayload()
                .getExpiration().after(new Date());
    }

    public String getUsername(String token){
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public JwtResponseDto extractClaims(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String role= claims.get("role").toString();
        String username= claims.getSubject();
        return new JwtResponseDto(role,username);
    }

   /* private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    } */
   private SecretKey getSignKey() {
       return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
   }
}
