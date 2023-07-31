package com.gustavo.security.modules.config;

import com.gustavo.security.modules.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@Component
public class SecurityFilter extends OncePerRequestFilter {
  TokenService tokenService;
  UserRepository userRepository;

  public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
    this.tokenService = tokenService;
    this.userRepository = userRepository;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    if(request.getRequestURI() == "/auth/login" || request.getRequestURI() == "/auth/register") return;

    var token = request.getCookies() != null ? recoverToken(request, "token") : "";
    System.out.println("Logo depois de pegar o token");
    if(token != ""){
      var subject = tokenService.validateToken(token);
      UserDetails user = userRepository.findByusername(subject);

      var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(request, response);
    System.out.println("logo antes de exceptioon");
    if(token == "") throw new RuntimeException("Token inválido");

  }

  private String recoverToken(HttpServletRequest request, String cookieName) {
    if(request != null)
    return Arrays.stream(request.getCookies())
            .filter(cookie -> cookieName.equals(cookie.getName()))
            .map(Cookie::getValue)
            .findAny().map(Object::toString).orElse("");
    return "";
  }
}