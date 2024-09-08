package com.ecomproject.product_service.service;

import com.ecomproject.product_service.dto.ProductRequest;
import com.ecomproject.product_service.dto.ProductResponse;
import com.ecomproject.product_service.model.Product;
import com.ecomproject.product_service.repository.ProductRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;

  public void createProduct(ProductRequest productRequest) {
      Product product = Product.builder()
          .name(productRequest.getName())
          .description(productRequest.getDescription())
          .price(productRequest.getPrice())
          .build();
      productRepository.save(product);
      log.info("Product {} saved successfully", product.getId());
  }

  public List<ProductResponse> getAllProducts() {
    log.info("Fetching all products");
    List<Product> products = productRepository.findAll();
    return products.stream().map(this::mapToProductResponse).toList();
  }

  public ProductResponse getProductById(String id) throws RuntimeException {
    Optional<Product> product = productRepository.findById(id);
    if (product.isEmpty()) {
      log.error("Product with id {} not found", id);
      throw new RuntimeException("Product not found");
    }
    return mapToProductResponse(product.get());
  }

  public void updateProduct(String id, ProductRequest productRequest) {
    Optional<Product> product = productRepository.findById(id);
    if (product.isEmpty()) {
      log.error("Product with id {} not found", id);
      throw new RuntimeException("Product not found");
    }

    Product updatedProduct = product.get();
    updatedProduct.setName(productRequest.getName());
    updatedProduct.setDescription(productRequest.getDescription());
    updatedProduct.setPrice(productRequest.getPrice());
    productRepository.save(updatedProduct);
    log.info("Product {} updated successfully", id);
  }

  public void deleteProduct(String id) {
    Optional<Product> product = productRepository.findById(id);
    if (product.isEmpty()) {
      log.error("Product with id {} not found", id);
      throw new RuntimeException("Product not found");
    }
    productRepository.delete(product.get());
    log.info("Product {} deleted successfully", id);
  }

  public void patchProduct(String id, ProductRequest productRequest) {
    Optional<Product> product = productRepository.findById(id);
    if (product.isEmpty()) {
      log.error("Product with id {} not found", id);
      throw new RuntimeException("Product not found");
    }

    Product updatedProduct = product.get();
    if (StringUtils.isNotBlank(productRequest.getName())) {
      updatedProduct.setName(productRequest.getName());
    }
    if (StringUtils.isNotBlank(productRequest.getDescription())) {
      updatedProduct.setDescription(productRequest.getDescription());
    }
    if (productRequest.getPrice() != null) {
      updatedProduct.setPrice(productRequest.getPrice());
    }
    productRepository.save(updatedProduct);
    log.info("Product {} patched successfully", id);
  }

  private ProductResponse mapToProductResponse(Product product) {
    return ProductResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .build();
  }
}
