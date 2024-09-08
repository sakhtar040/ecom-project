package com.ecomproject.product_service.controller;

import com.ecomproject.product_service.dto.ProductRequest;
import com.ecomproject.product_service.dto.ProductResponse;
import com.ecomproject.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
  private final ProductService productService;

  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public void createProduct(@RequestBody ProductRequest productRequest) {
    productService.createProduct(productRequest);
  }

  @GetMapping("/all")
  @ResponseStatus(HttpStatus.OK)
  public List<ProductResponse> getAllProducts() {
    return productService.getAllProducts();
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ProductResponse getProductById(@PathVariable String id) {
    return productService.getProductById(id);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void updateProduct(@PathVariable String id, @RequestBody ProductRequest productRequest) {
    productService.updateProduct(id, productRequest);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteProduct(@PathVariable String id) {
    productService.deleteProduct(id);
  }

  @PatchMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void patchProduct(@PathVariable String id, @RequestBody ProductRequest productRequest) {
    productService.patchProduct(id, productRequest);
  }
}
