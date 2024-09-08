package com.ecomproject.product_service;

import com.ecomproject.product_service.dto.ProductRequest;
import com.ecomproject.product_service.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private ProductRepository productRepository;

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.0.10");

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = ProductRequest.builder()
						.name("product1")
						.description("product1 description")
						.price(100.0)
						.build();

		mockMvc.perform(
						MockMvcRequestBuilders
										.post("/api/v1/product/create")
										.contentType(MediaType.APPLICATION_JSON)
										.content(mapper.writeValueAsString(productRequest))
										.accept(MediaType.APPLICATION_JSON)
						).andExpect(status().isCreated());

		Assertions.assertEquals(1, productRepository.count());
	}

}
