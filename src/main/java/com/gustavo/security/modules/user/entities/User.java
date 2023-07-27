package com.gustavo.security.modules.user.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "users")
public class User implements UserDetails {

  public User(String name, String username, String password, Role role){
    this.name = name;
    this.username = username;
    this.password = password;
    this.role = role;
  }

  public User(){}

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Role role = Role.USER;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return switch (this.role){
      case CEO -> List.of(new SimpleGrantedAuthority("CEO"),
              new SimpleGrantedAuthority("MANAGER"),
              new SimpleGrantedAuthority("ADMIN"),
              new SimpleGrantedAuthority("USER"));
      case MANAGER -> List.of(new SimpleGrantedAuthority("MANAGER"),
              new SimpleGrantedAuthority("ADMIN"),
              new SimpleGrantedAuthority("USER"));
      case ADMIN -> List.of(new SimpleGrantedAuthority("ADMIN"),
              new SimpleGrantedAuthority("USER"));
      case USER -> List.of(new SimpleGrantedAuthority("USER"));
    };
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public String getPassword(){
    return this.password;
  }

  @Override
  public String getUsername(){
    return this.username;
  }


}
