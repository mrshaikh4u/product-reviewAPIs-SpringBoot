package com.mrshaikh4u.demos.reviewservice.to;

import javax.validation.constraints.NotNull;
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
@ApiModel(description = "All details about reviews")
@AllArgsConstructor
@NoArgsConstructor
public class CustomerReviewTO implements Validatable {
    
    @ApiModelProperty(notes = "Auto generated review ID")
    private Long reviewID;
    
    @ApiModelProperty(notes = "userName")
    private String userName;
    
    @ApiModelProperty(notes = "Product id")
    @NotNull(message = "Product ID can not be empty")
    private String productID;
    
    @ApiModelProperty(notes = "Product's review score")
    @NotNull(message = "Review score can not be empty")
    private Float reviewScore;
    
    @ApiModelProperty(notes = "Product's review comment")
    private String reviewComment;
    
    /**
    * place holder for more business validations those can be added later
    */
    @Override
    public boolean validate() throws InputParameterInvalidException {
        return true;
    }
    
}
