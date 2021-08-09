package com.mrshaikh4u.demos.reviewservice.controller;

import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import com.mrshaikh4u.demos.reviewservice.config.ApplicationConfiguration;
import com.mrshaikh4u.demos.reviewservice.exceptions.DataNotFoundException;
import com.mrshaikh4u.demos.reviewservice.exceptions.DataNotSavedException;
import com.mrshaikh4u.demos.reviewservice.service.ReviewService;
import com.mrshaikh4u.demos.reviewservice.to.AggregateReviewTO;
import com.mrshaikh4u.demos.reviewservice.to.CustomerReviewTO;
import com.mrshaikh4u.demos.reviewservice.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/v1")
@Api(value = "review Management APIs", tags = {ApplicationConfiguration.REVIEW_CONTROLLER_CLASS})
public class ReviewController {
    
    @Autowired
    protected ReviewService reviewService;
    
    @PostMapping("/reviews")
    @ApiOperation(value = "Submit new review,returns submitted review", response = CustomerReviewTO.class)
    public CustomerReviewTO submitReview(
    @Valid @RequestBody @ApiParam(value = "input review details that needs to be submitted", required = true) CustomerReviewTO inputReview,Principal user)
    throws DataNotSavedException {
        if(user==null){
            throw new BadCredentialsException(Constants.MSG_INVALID_TOKEN_ERR);
        }
        return reviewService.submitReview(inputReview,user.getName());
    }
    
    @PutMapping("/reviews/{reviewID}")
    @ApiOperation(value = "Update review in the system ,returns modified review details", response = CustomerReviewTO.class)
    public CustomerReviewTO updateReview(@Valid @RequestBody @ApiParam(value = "review to update") CustomerReviewTO input,
    @PathVariable @ApiParam(value = "reviewID which needs to be updated") Long reviewID,Principal user)
    throws DataNotSavedException {
        if(user==null){
            throw new BadCredentialsException(Constants.MSG_INVALID_TOKEN_ERR);
        }
        input.setReviewID(reviewID);
        return reviewService.updateReview(input,user.getName());
    }
    
    @DeleteMapping("/reviews/{reviewID}")
    @ApiOperation(value = "Delete review in the system ,returns 200 success OK upon succesful deletion")
    public void deleteReview(@PathVariable @ApiParam(value = "review id to be deleted") Long reviewID,Principal user)
    throws DataNotSavedException {
        if(user==null){
            throw new BadCredentialsException(Constants.MSG_INVALID_TOKEN_ERR);
        }
        reviewService.deleteReview(reviewID,user.getName());
    }
    
    @GetMapping("/reviews/{productID}")
    @ApiOperation(value = "Get summary of reviews", response = AggregateReviewTO.class)
    public AggregateReviewTO getReviewsAggregate(@PathVariable @ApiParam(value = "product id to get aggregate") String productID)
    throws DataNotFoundException {
        return reviewService.getReviewsAggregate(productID);
    }
    
    @GetMapping("/reviews/all/{productID}")
    @ApiOperation(value = "Fetch all available reviews for given product")
    public List<CustomerReviewTO> fetchAllReviews(@PathVariable @ApiParam(value = "product id to fetch reviews") String productID) throws DataNotFoundException {
        return reviewService.fetchAllReviews(productID);
    }
    
}
