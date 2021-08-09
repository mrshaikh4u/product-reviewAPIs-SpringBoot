package com.mrshaikh4u.demos.reviewservice.service;

import com.mrshaikh4u.demos.reviewservice.exceptions.DataNotSavedException;
import com.mrshaikh4u.demos.reviewservice.to.UserDetailsTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsService extends UserDetailsService{
    public boolean signUp(UserDetailsTO userDetailsTO) throws DataNotSavedException;
}
