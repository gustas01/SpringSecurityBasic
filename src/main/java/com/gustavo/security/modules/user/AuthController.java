package com.gustavo.security.modules.user;

import com.gustavo.security.modules.config.TokenService;
import com.gustavo.security.modules.user.DTOs.AuthenticationDTO;
import com.gustavo.security.modules.user.DTOs.RegisterDTO;
import com.gustavo.security.modules.user.entities.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("auth")
public class AuthController {
  private AuthenticationManager authenticationManager;
  private UserRepository userRepository;
  private TokenService tokenService;

  public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, TokenService tokenService) {
      this.authenticationManager = authenticationManager;
      this.userRepository = userRepository;
      this.tokenService = tokenService;
  }

  @PostMapping("/login")
  public ResponseEntity login( @RequestBody @NotNull AuthenticationDTO user){

    Authentication usernamePassoword = new UsernamePasswordAuthenticationToken(user.username(), user.password());
    var auth = this.authenticationManager.authenticate(usernamePassoword);
    var token = tokenService.generateToken(user);

//    Map<String, HttpServletResponse> tokenObject = new HashMap<>();
//    tokenObject.put("token", token);

    return ResponseEntity.ok("Autenticação realizada com sucesso!");
  }

  @PostMapping("/register")
  public ResponseEntity register(@RequestBody RegisterDTO user){
      if(this.userRepository.findByusername(user.username()) != null) return ResponseEntity.badRequest().build();

      String encryptedPassword = new BCryptPasswordEncoder().encode(user.password());
      User newUser = new User(user.name(), user.username(), encryptedPassword, user.role());

      this.userRepository.save(newUser);

      return ResponseEntity.ok("Usuário criado com sucesso!");
  }
}
