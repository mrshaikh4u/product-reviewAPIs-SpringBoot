package com.mrshaikh4u.demos.productservice.service;

import com.mrshaikh4u.demos.productservice.exceptions.DataNotFoundException;
import com.mrshaikh4u.demos.productservice.to.ProductDetailsTO;
import org.springframework.web.client.RestClientException;

public interface ProductService {
    public ProductDetailsTO getProductDetails(String productID) throws RestClientException,DataNotFoundException;
}
