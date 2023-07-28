package com.gustavo.security.modules.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.gustavo.security.modules.user.DTOs.AuthenticationDTO;
import com.gustavo.security.modules.user.entities.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {
  @Value("${api.security.token.secret}")
  private String secret;
  HttpServletResponse httpServletResponse;

  public TokenService(HttpServletResponse httpServletResponse) {
    this.httpServletResponse = httpServletResponse;
  }

  public HttpServletResponse generateToken(AuthenticationDTO user) {
    try {
      Map<String, String> tokenPayload = new HashMap<>();
      tokenPayload.put("username", user.password());

      Algorithm algorithm = Algorithm.HMAC256(secret);
      String token = JWT.create().withIssuer("auth-api").withSubject(user.username())
              .withPayload(tokenPayload)
              .withExpiresAt(generateExpirationDate())
              .sign(algorithm);
      Cookie cookie = new Cookie("token", token);
      cookie.setHttpOnly(true);
      httpServletResponse.addCookie(cookie);
//      httpServletResponse.getWriter().write("Token gerado com sucesso");
      return httpServletResponse;
    } catch (JWTCreationException exception) {
      throw new RuntimeException("Erro ao gerar token", exception);
    }
  }

  public String validateToken(String token){
    try{
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.require(algorithm).withIssuer("auth-api").build().verify(token).getSubject();
    }catch (JWTVerificationException exception){
      return "";
    }
  }

  private Instant generateExpirationDate(){
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }
}