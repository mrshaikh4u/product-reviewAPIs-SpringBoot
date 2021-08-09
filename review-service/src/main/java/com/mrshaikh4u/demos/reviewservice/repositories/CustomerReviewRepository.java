package com.mrshaikh4u.demos.reviewservice.repositories;

import java.util.List;
import com.mrshaikh4u.demos.reviewservice.domain.CustomerReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for Contact Entity
 * 
 * @author Rahil
 *
 */
@Repository
public interface CustomerReviewRepository extends JpaRepository<CustomerReview, Long> {
    public List<CustomerReview> findByProductID(String productID);
}
