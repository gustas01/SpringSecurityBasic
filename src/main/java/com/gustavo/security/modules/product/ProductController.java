package com.gustavo.security.modules.product;

import com.gustavo.security.modules.product.entities.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
  private ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public List<Product> index(){
    return productService.index();
  }

  @PostMapping
  public Product create(@RequestBody Product product){
    return productService.create(product);
  }
}
