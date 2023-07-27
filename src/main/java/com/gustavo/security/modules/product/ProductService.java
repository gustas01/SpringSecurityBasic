package com.gustavo.security.modules.product;

import com.gustavo.security.modules.product.entities.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
public class ProductService {
  private ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }


  public List<Product> index(){
    return productRepository.findAll();
  }

  public Product create(Product product){
    return productRepository.save(product);
  }
}
