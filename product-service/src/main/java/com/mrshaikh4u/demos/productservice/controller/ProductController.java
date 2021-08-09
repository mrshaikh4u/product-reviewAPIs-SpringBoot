package com.mrshaikh4u.demos.productservice.controller;

import com.mrshaikh4u.demos.productservice.config.ApplicationConfiguration;
import com.mrshaikh4u.demos.productservice.exceptions.DataNotFoundException;
import com.mrshaikh4u.demos.productservice.service.ProductService;
import com.mrshaikh4u.demos.productservice.to.ProductDetailsTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/v1")
@Api(value = "product Management APIs", tags = {ApplicationConfiguration.REVIEW_CONTROLLER_CLASS})
public class ProductController {
    
    @Autowired
    protected ProductService productService;
    
    @GetMapping("/products/{productID}")
    @ApiOperation(value = "Get product details", response = ProductDetailsTO.class)
    public ProductDetailsTO getProductDetails(@PathVariable @ApiParam(value = "product id to get details") String productID)
    throws DataNotFoundException {
        return productService.getProductDetails(productID);
    }
    
   
    
}
