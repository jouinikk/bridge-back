package com.example.cars.config;

import com.example.cars.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "jXnfmueoYNG/paPUaMY241bFlgEFhEG6CEjE6zUmuEI=" ;
    public String getUserEmail(String jwtToken) {
        return extractClaim(jwtToken,Claims::getSubject);
    }
    private Claims extractAllClaims(String token)
    {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public <T> T extractClaim(String tocken , Function<Claims,T> claimResolver){
        final Claims claims = extractAllClaims(tocken);
        return claimResolver.apply(claims);
    }

    public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails){
        if (userDetails instanceof User){
            User user = (User) userDetails;
            extraClaims.put("name",user.getName());
            extraClaims.put("id",user.getId());
            extraClaims.put("role",user.getRole().toString());
        }

    return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000*60*24))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        final String userName = getUserEmail(token);
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

}
