package com.mrshaikh4u.demos.productservice.to;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Customer Review Transfer object
 * 
 * @author Rahil
 *
 */
@Data
@ApiModel(description = "All details about product")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDetailsTO {

    @ApiModelProperty(notes = "Product id")
    private String productID;

    @ApiModelProperty(notes = "Product's average review score")
    private Float averageScore;

    @ApiModelProperty(notes = "Product's total reviews count")
    private Integer totalReviews;

    @ApiModelProperty(notes = "product type")
    private String product_type;

    @ApiModelProperty(notes = "Product's model number")
    private String model_number;

    @ApiModelProperty(notes = "Product's name")
    private String name;

    @ApiModelProperty(notes = "Product's attributes")
    private Object attribute_list;


}
