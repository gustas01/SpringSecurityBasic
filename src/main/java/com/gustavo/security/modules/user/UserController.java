package com.gustavo.security.modules.user;

import com.gustavo.security.modules.user.entities.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public List<User> index (){
    return userService.index();
  }

  @PostMapping
  public User create(@RequestBody User user){
    return userService.create(user);
  }
}
