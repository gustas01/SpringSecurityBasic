package com.gustavo.security.modules.product;

import com.gustavo.security.modules.product.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> { }
