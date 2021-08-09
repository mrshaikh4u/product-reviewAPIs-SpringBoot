package com.mrshaikh4u.demos.reviewservice.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import com.mrshaikh4u.demos.reviewservice.domain.CustomUserDetails;
import com.mrshaikh4u.demos.reviewservice.exceptions.DataNotFoundException;
import com.mrshaikh4u.demos.reviewservice.exceptions.DataNotSavedException;
import com.mrshaikh4u.demos.reviewservice.repositories.UserDetailsRepository;
import com.mrshaikh4u.demos.reviewservice.to.UserDetailsTO;
import com.mrshaikh4u.demos.reviewservice.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserDetailsServiceImpl implements CustomUserDetailsService{
    
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private PasswordEncoder encoder;
    
    @Override
    public boolean signUp(UserDetailsTO userDetailsTO) throws DataNotSavedException {
        CustomUserDetails userDetails = null;
        userDetailsTO.setPassword(encoder.encode(userDetailsTO.getPassword()));
        try {
           userDetails = mapper.getMapperInstance().map(userDetailsTO, CustomUserDetails.class);
           userDetails = userDetailsRepository.save(userDetails);
       } catch (Exception ex) {
           log.error("Unable to add review " + userDetailsTO);
           throw new DataNotSavedException("Unable to signup", ex);
       }
       return userDetails != null;
    }
    @Override
    public UserDetails loadUserByUsername(String userId) throws DataNotFoundException {
		Optional<CustomUserDetails> user = userDetailsRepository.findById(userId);
		if(!user.isPresent()){
            String errorMsg = "User with id : " + userId + " not found";
            log.error(errorMsg);
            throw new DataNotFoundException(errorMsg);
		}
		return new org.springframework.security.core.userdetails.User(user.get().getUserName(), user.get().getPassword(), getAuthority());
	}
    /*
    * Roles can be implemented later  
    */
    private Collection<? extends GrantedAuthority> getAuthority() {
        return new ArrayList<>();
    }

}
    
    
