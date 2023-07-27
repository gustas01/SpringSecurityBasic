package com.gustavo.security.modules.user;

import com.gustavo.security.modules.user.entities.User;
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

  public User create(User user){
    return userRepository.save(user);
  }
}
