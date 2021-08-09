package com.mrshaikh4u.demos.reviewservice.to;

import javax.validation.constraints.NotEmpty;
import com.mrshaikh4u.demos.reviewservice.exceptions.InputParameterInvalidException;
import com.mrshaikh4u.demos.reviewservice.service.Validatable;
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
@ApiModel(description = "All details about contact")
@AllArgsConstructor
@NoArgsConstructor
public class AggregateReviewTO implements Validatable {
    
    @ApiModelProperty(notes = "Product id")
    @NotEmpty(message = "Product ID can not be empty")
    private String productID;
    
    @ApiModelProperty(notes = "Product's average review score")
    @NotEmpty(message = "Review score can not be empty")
    private Float averageScore;
    
    @ApiModelProperty(notes = "Product's total reviews count")
    private Integer totalReviews;
    
    /**
    * more business validations can be added later
    */
    @Override
    public boolean validate() throws InputParameterInvalidException {
        return true;
    }
    
}
