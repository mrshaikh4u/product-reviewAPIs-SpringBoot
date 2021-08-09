package com.mrshaikh4u.demos.productservice.service;

import com.mrshaikh4u.demos.productservice.to.ProductDetailsTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;


@ExtendWith(SpringExtension.class)
public class ProductServiceImplTest {

	@TestConfiguration
	static class ProductServiceImplTestContextConfiguration {

		@Bean
		public ProductService productService() {
			return new ProductServiceImpl();
		}

		@Bean
		public RestTemplate restTemplate() {
			return new RestTemplate();
		}

	}

	@Autowired
	private ProductService productService;


	@Test
	public void whenGet_thenProductDetailsShouldBeReturned() {
		ProductDetailsTO productDetails = productService.getProductDetails("BB5476");
		Assertions.assertNotNull(productDetails);
		Assertions.assertDoesNotThrow(() -> productService.getProductDetails("BB5476"));
	}



}
