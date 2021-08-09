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
* User Details Transfer object
* 
* @author Rahil
*
*/
@Data
@ApiModel(description = "All details about User")
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsTO implements Validatable {
    
    @ApiModelProperty(notes = "customer userName")
    @NotEmpty(message = "username can't be empty")
    private String userName;
    
    @ApiModelProperty(notes = "password")
    @NotEmpty(message = "password can't be empty")
    private String password;
    
    @ApiModelProperty(notes = "customer email")
    private String email;

    @Override
    public boolean validate() throws InputParameterInvalidException {
        return true;
    }
    
}
