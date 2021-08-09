package com.mrshaikh4u.demos.reviewservice.controller;

import javax.validation.Valid;
import com.mrshaikh4u.demos.reviewservice.config.ApplicationConfiguration;
import com.mrshaikh4u.demos.reviewservice.exceptions.DataNotSavedException;
import com.mrshaikh4u.demos.reviewservice.service.AuthenticationRequest;
import com.mrshaikh4u.demos.reviewservice.service.AuthenticationResponse;
import com.mrshaikh4u.demos.reviewservice.service.CustomUserDetailsService;
import com.mrshaikh4u.demos.reviewservice.to.UserDetailsTO;
import com.mrshaikh4u.demos.reviewservice.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/v1")
@Api(value = "user Management APIs", tags = {ApplicationConfiguration.REVIEW_CONTROLLER_CLASS})
public class UserController {
    
    @Autowired
    protected CustomUserDetailsService userDetailsService;


    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/signup")
    @ApiOperation(value = "Submit new review,returns submitted review", response = ResponseEntity.class)
    public ResponseEntity<String> userSignup(
    @Valid @RequestBody @ApiParam(value = "input review details that needs to be submitted", required = true) UserDetailsTO inputUser)
    throws DataNotSavedException {
        return userDetailsService.signUp(inputUser) ? ResponseEntity.ok("Signup success") : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest request) throws BadCredentialsException{
        try{
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        }catch(BadCredentialsException ex){
            throw new BadCredentialsException("incorrect username or password");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserName());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
    
}
