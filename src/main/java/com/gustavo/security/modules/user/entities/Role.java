package com.gustavo.security.modules.user.entities;

public enum Role {
  USER("user"), ADMIN("admin"), MANAGER("manager"), CEO("ceo");

  private String role;

  private Role(String role){
    this.role = role;
  }

  public String getRole(){
    return this.role;
  }
}
