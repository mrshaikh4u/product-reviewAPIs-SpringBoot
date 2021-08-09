package com.mrshaikh4u.demos.reviewservice.service;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import com.mrshaikh4u.demos.reviewservice.domain.CustomerReview;
import com.mrshaikh4u.demos.reviewservice.exceptions.DataNotSavedException;
import com.mrshaikh4u.demos.reviewservice.repositories.CustomerReviewRepository;
import com.mrshaikh4u.demos.reviewservice.to.CustomerReviewTO;
import com.mrshaikh4u.demos.reviewservice.utils.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ReviewServiceImplTest {

	
	@TestConfiguration
	static class ReviewServiceImplTestContextConfiguration {

		@Bean
		public ReviewService reviewService() {
			return new ReviewServiceImpl();
		}

		@Bean
		public ObjectMapper reviewMapper() {
			return new ObjectMapper();
		}

	}

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private CustomerReviewRepository reviewRepo;

	@BeforeEach
	public void setUp() {
		CustomerReview tempInst = any(CustomerReview.class);
		when(reviewRepo.save(tempInst)).then(returnsFirstArg());
		CustomerReview CustomerReview = new CustomerReview(101l, "rahilTest", "P111", 3.5f, "very good");
		Optional<CustomerReview> optionalCustomerReview = Optional.of(CustomerReview);
		when(reviewRepo.findById(Mockito.anyLong())).thenReturn(optionalCustomerReview);
		when(reviewRepo.findAll()).thenReturn(prepareCustomerReviewList());
		doNothing().when(reviewRepo)
		           .deleteById(Mockito.anyLong());
		when(reviewRepo.existsById(Mockito.anyLong())).thenReturn(true);

	}

	private List<CustomerReview> prepareCustomerReviewList() {
		return Arrays.asList(new CustomerReview(101l, "rahilTest", "P111", 3.5f, "very good"));
	}

	@Test
	public void whenAdd_thenCustomerReviewShouldBeAdded() throws DataNotSavedException {
		CustomerReviewTO inputCustomerReview = new CustomerReviewTO(101l, "rahilTest", "P111", 3.5f, "very good");
		CustomerReviewTO addedCustomerReview = reviewService.submitReview(inputCustomerReview,"rahilTest");
		Assertions.assertEquals(addedCustomerReview.getUserName(),inputCustomerReview.getUserName());
	}

	@Test
	public void whenModify_thenCustomerReviewShouldBeModified() throws DataNotSavedException {
		CustomerReviewTO inputCustomerReview = new CustomerReviewTO(101l, "rahilTest", "P111", 3.5f, "very good");
		CustomerReview CustomerReview = mapper.getMapperInstance()
		                        .map(inputCustomerReview, CustomerReview.class);
		reviewRepo.save(CustomerReview);
		inputCustomerReview.setReviewScore(2.2f);
		CustomerReviewTO modfiedCustomerReview = reviewService.updateReview(inputCustomerReview,"rahilTest");
		Assertions.assertEquals(modfiedCustomerReview.getReviewScore(),inputCustomerReview.getReviewScore());
	}

	@Test
	public void whenDelete_thenCustomerReviewShouldBeDeleted() throws DataNotSavedException {
		CustomerReviewTO inputCustomerReview = new CustomerReviewTO(101l, "rahilTest", "P111", 3.5f, "very good");
		CustomerReviewTO addedCustomerReview = reviewService.submitReview(inputCustomerReview,"rahilTest");
		addedCustomerReview.setReviewID(inputCustomerReview.getReviewID());
		Assertions.assertTrue(reviewService.deleteReview(addedCustomerReview.getReviewID(),"rahilTest"));
	}
	
}
