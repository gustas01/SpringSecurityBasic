package com.gustavo.security.modules.product.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(nullable = false, length = 60)
  private String name;

  @Column(nullable = false)
  private double price;
}
