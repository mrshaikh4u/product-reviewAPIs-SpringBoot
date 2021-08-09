package com.mrshaikh4u.demos.reviewservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.mrshaikh4u.demos.reviewservice.domain.CustomerReview;
import com.mrshaikh4u.demos.reviewservice.exceptions.DataMappingException;
import com.mrshaikh4u.demos.reviewservice.exceptions.DataNotFoundException;
import com.mrshaikh4u.demos.reviewservice.exceptions.InputParameterInvalidException;
import com.mrshaikh4u.demos.reviewservice.exceptions.OperationNotPermittedException;
import com.mrshaikh4u.demos.reviewservice.repositories.CustomerReviewRepository;
import com.mrshaikh4u.demos.reviewservice.to.AggregateReviewTO;
import com.mrshaikh4u.demos.reviewservice.to.CustomerReviewTO;
import com.mrshaikh4u.demos.reviewservice.utils.Constants;
import com.mrshaikh4u.demos.reviewservice.utils.ObjectMapper;
import org.modelmapper.ConfigurationException;
import org.modelmapper.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    protected ObjectMapper reviewMapper;

    @Autowired
    protected CustomerReviewRepository customerReviewRepository;

    /**
     * This operation adds new review, supports writable transaction and performs input validation
     * before saving
     * 
     * @param customerReviewTO - Service input
     * @return reviewTO - transfer object
     */
    @Override
    @Transactional
    public CustomerReviewTO submitReview(CustomerReviewTO customerReviewTO, String userName)
            throws InputParameterInvalidException, DataMappingException {
        log.info("User : " + userName + " trying to submit review" + customerReviewTO);
        customerReviewTO.setUserName(userName);
        validateInput(customerReviewTO);
        CustomerReview savedReview = null;
        try {
            var customerReview =
                    reviewMapper.getMapperInstance().map(customerReviewTO, CustomerReview.class);
            savedReview = customerReviewRepository.save(customerReview);
        } catch (IllegalArgumentException | ConfigurationException | MappingException ex) {
            log.error("Unable to add review " + ex.getMessage());
            throw new InputParameterInvalidException(ex.getMessage(), "customerReviewTO");
        }
        try {
            return reviewMapper.getMapperInstance().map(savedReview, CustomerReviewTO.class);
        } catch (IllegalArgumentException | ConfigurationException | MappingException ex) {
            log.error(Constants.MSG_MAPPING_ERR + ex.getMessage());
            throw new DataMappingException(ex.getMessage(), ex);
        }
    }

    @Override
    @Transactional
    public CustomerReviewTO updateReview(CustomerReviewTO customerReviewTO, String userName)
            throws DataNotFoundException, InputParameterInvalidException,
            OperationNotPermittedException, DataMappingException {
        log.info("Trying to modify review" + customerReviewTO);
        if (customerReviewTO.getReviewID() == null) {
            throw new InputParameterInvalidException("customerReviewTO",
                    "reviewId not passed in input ", Constants.FLD_REVIEW_ID, "null/empty");
        }
        Optional<CustomerReview> savedReview;
        try {
            savedReview = customerReviewRepository.findById(customerReviewTO.getReviewID());
        } catch (IllegalArgumentException ex) {
            String errorMsg = new StringBuilder("Error occured while fetching review ")
                    .append(customerReviewTO.getReviewID()).toString();
            log.error(errorMsg);
            throw new InputParameterInvalidException(errorMsg, ex);
        }
        if (!savedReview.isPresent()) {
            String errorMsg = "Review with id : " + customerReviewTO.getReviewID() + " not found";
            log.error(errorMsg);
            throw new DataNotFoundException(errorMsg);
        }
        CustomerReview savedReviewEntity = savedReview.get();
        if (!savedReviewEntity.getUserName().equals(userName)) {
            String errorMsg = "requested operation not permitted for this user";
            log.error(errorMsg);
            throw new OperationNotPermittedException(errorMsg);
        }
        prepareReviewToUpdate(savedReviewEntity, customerReviewTO);
        CustomerReview updatedReview = null;
        try {
            updatedReview = customerReviewRepository.save(savedReviewEntity);
        } catch (IllegalArgumentException ex) {
            String errorMsg = new StringBuilder("Error occured while modifying review ")
                    .append(savedReviewEntity).toString();
            log.error(errorMsg);
            throw new InputParameterInvalidException(errorMsg, ex);
        }
        try {
            return reviewMapper.getMapperInstance().map(updatedReview, CustomerReviewTO.class);
        } catch (IllegalArgumentException | ConfigurationException | MappingException ex) {
            log.error(Constants.MSG_MAPPING_ERR + ex.getMessage());
            throw new DataMappingException(ex.getMessage(), ex);
        }
    }

    @Override
    @Transactional
    public boolean deleteReview(Long reviewID, String userName)
            throws InputParameterInvalidException, DataNotFoundException,
            OperationNotPermittedException {
        if (reviewID == null) {
            throw new InputParameterInvalidException(Constants.FLD_REVIEW_ID,
                    "reviewId not passed in input ", "reviewID", "null/empty");
        }
        Optional<CustomerReview> savedReview;
        try {
            savedReview = customerReviewRepository.findById(reviewID);
        } catch (IllegalArgumentException ex) {
            String errorMsg = new StringBuilder("Error occured while fetching review ")
                    .append(reviewID).toString();
            log.error(errorMsg);
            throw new InputParameterInvalidException(errorMsg, ex);
        }
        if (!savedReview.isPresent()) {
            throw new DataNotFoundException("review id " + reviewID + "Not found");
        }
        if (!savedReview.get().getUserName().equals(userName)) {
            String errorMsg = "requested operation not permitted for this user";
            log.error(errorMsg);
            throw new OperationNotPermittedException(errorMsg);
        }
        try {
            customerReviewRepository.deleteById(reviewID);
        } catch (IllegalArgumentException ex) {
            String errorMsg = new StringBuilder("Error occured while deleting review ")
                    .append(reviewID).toString();
            log.error(errorMsg);
            throw new InputParameterInvalidException(errorMsg, ex);
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerReviewTO> fetchAllReviews(String productID)
            throws DataNotFoundException, DataMappingException {
        List<CustomerReview> allReviews;
        try {
            allReviews = customerReviewRepository.findByProductID(productID);
        } catch (Exception ex) {
            String errorMsg = "Error occured while fetching reviews ";
            log.error(errorMsg);
            throw new DataNotFoundException(errorMsg, ex);
        }
        if (CollectionUtils.isEmpty(allReviews)) {
            log.error("No reviews found");
            throw new DataNotFoundException("No reviews found");
        }
        try {
            return allReviews.stream()
                    .map(e -> reviewMapper.getMapperInstance().map(e, CustomerReviewTO.class))
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException | ConfigurationException | MappingException ex) {
            log.error(Constants.MSG_MAPPING_ERR + ex.getMessage());
            throw new DataMappingException(ex.getMessage(), ex);
        }

    }

    @Override
    @Transactional(readOnly = true)
    public AggregateReviewTO getReviewsAggregate(String productID) throws DataNotFoundException {
        List<CustomerReviewTO> allReviews = fetchAllReviews(productID);
        if (CollectionUtils.isEmpty(allReviews)) {
            String errorMsg = "Error occured while fetching product reviews ";
            log.error(errorMsg);
            throw new DataNotFoundException(errorMsg);
        }
        double averageScore = allReviews.stream().mapToDouble(CustomerReviewTO::getReviewScore)
                .average().getAsDouble();
        AggregateReviewTO aggregateReviewTO = new AggregateReviewTO();
        aggregateReviewTO.setProductID(productID);
        aggregateReviewTO.setAverageScore((float) (Math.round(averageScore * 100) / 100D));
        aggregateReviewTO.setTotalReviews(allReviews.size());
        return aggregateReviewTO;
    }

    /**
     * This method performs REST input validation as this method is programmed to interface ,
     * implementation can be changed at any time in future without touching this code
     * 
     * @param inputReview
     */
    private void validateInput(CustomerReviewTO inputReview) {
        boolean isValid = inputReview.validate();
        if (isValid) {
            log.info("input validation passed");
        }
    }

    /**
     * This method maps review details passed in input to entity to be modified
     * 
     * @param reviewToSave - Entity to be modified
     * @param inputReview - input to the service
     */
    private void prepareReviewToUpdate(CustomerReview reviewToSave, CustomerReviewTO inputReview) {
        log.info(new StringBuilder("preparing object to be modified using ").append(inputReview)
                .toString());
        if (inputReview.getReviewScore() != null) {
            reviewToSave.setReviewScore(inputReview.getReviewScore());
        }
        if (inputReview.getReviewComment() != null && !inputReview.getReviewComment().isBlank()) {
            reviewToSave.setReviewComment(inputReview.getReviewComment());
        }

    }

}
