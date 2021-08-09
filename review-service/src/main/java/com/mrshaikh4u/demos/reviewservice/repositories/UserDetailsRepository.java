package com.mrshaikh4u.demos.reviewservice.repositories;

import java.util.Optional;
import com.mrshaikh4u.demos.reviewservice.domain.CustomUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for User Entity
 * 
 * @author Rahil
 *
 */
@Repository
public interface UserDetailsRepository extends JpaRepository<CustomUserDetails, String>{
    @Override
    public Optional<CustomUserDetails> findById(String id);

}
