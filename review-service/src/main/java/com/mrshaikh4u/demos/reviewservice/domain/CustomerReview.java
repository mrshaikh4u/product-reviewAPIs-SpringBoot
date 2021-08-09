package com.mrshaikh4u.demos.reviewservice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PRODUCT_REVIEWS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerReview {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "review_id")
	private Long reviewID;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "product_id")
	private String productID;

	@Column(name = "review_score")
	private Float reviewScore;

	@Column(name = "review_comment")
	private String reviewComment;

}
