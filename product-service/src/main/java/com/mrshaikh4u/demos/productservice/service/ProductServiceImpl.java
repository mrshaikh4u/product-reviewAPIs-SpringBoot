package com.mrshaikh4u.demos.productservice.service;

import com.mrshaikh4u.demos.productservice.exceptions.DataNotFoundException;
import com.mrshaikh4u.demos.productservice.exceptions.InputParameterInvalidException;
import com.mrshaikh4u.demos.productservice.to.ProductDetailsTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ProductDetailsTO getProductDetails(String productID) throws InputParameterInvalidException,RestClientException,DataNotFoundException {
        if (productID == null || productID.isEmpty() || productID.trim().isEmpty()){
            String errorMsg = "product id passed is null or empty";
            log.error(errorMsg);
            throw new InputParameterInvalidException(errorMsg);
        } 
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", "Application");
        HttpEntity<ProductDetailsTO> entity = new HttpEntity<>(headers);
        ProductDetailsTO apiResponseExternal = null;
        ProductDetailsTO apiResponseInternal = null;
        log.info("request sent");
        try {
            apiResponseExternal =
                    restTemplate.exchange("https://adidas.co.uk/api/products/" + productID,
                            HttpMethod.GET, entity, ProductDetailsTO.class).getBody();
        } catch (RestClientException ex) {
            var errorMessage = "Failed to get data from external product API";
            log.error(errorMessage);
            throw ex;
        }
        log.info("data returned from external service");
        try {
            apiResponseInternal =
                    restTemplate.exchange("http://localhost:8080/api/v1/reviews/" + productID,
                            HttpMethod.GET, entity, ProductDetailsTO.class).getBody();
        } catch (RestClientException ex) {
            var errorMessage = "Failed to get data from external review API";
            log.error(errorMessage);
            throw ex;
        }

        if (apiResponseInternal == null || apiResponseExternal == null) {
            throw new DataNotFoundException("Data could'nt be fetch from external services");
        }
        apiResponseExternal.setProductID(apiResponseInternal.getProductID());
        apiResponseExternal.setAverageScore(apiResponseInternal.getAverageScore());
        apiResponseExternal.setTotalReviews(apiResponseInternal.getTotalReviews());
        return apiResponseExternal;
    }

}
