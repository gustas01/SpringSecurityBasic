package com.gustavo.security.modules.user;

import com.gustavo.security.modules.user.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
  private UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> index (){
    return userRepository.findAll();
  }

  public ResponseEntity<User> create(User user){
    if(this.userRepository.findByusername(user.getUsername()) != null) return ResponseEntity.badRequest().build();
    user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
    return ResponseEntity.ok(userRepository.save(user));
  }
}
