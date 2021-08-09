package com.mrshaikh4u.demos.reviewservice.service;

import java.util.List;

import com.mrshaikh4u.demos.reviewservice.exceptions.DataNotFoundException;
import com.mrshaikh4u.demos.reviewservice.exceptions.DataNotSavedException;
import com.mrshaikh4u.demos.reviewservice.to.AggregateReviewTO;
import com.mrshaikh4u.demos.reviewservice.to.CustomerReviewTO;

/**
 * Contact service with supported operations
 * 
 * @author Rahil
 *
 */
public interface ReviewService {
	public CustomerReviewTO submitReview(CustomerReviewTO customerReviewTO, String userName) throws DataNotSavedException;

	public CustomerReviewTO updateReview(CustomerReviewTO customerReviewTO, String userName) throws DataNotSavedException;

	public boolean deleteReview(Long reviewID, String name) throws DataNotSavedException;

	public List<CustomerReviewTO> fetchAllReviews(String productID) throws DataNotFoundException;

	public AggregateReviewTO getReviewsAggregate(String productID) throws DataNotFoundException;


}
