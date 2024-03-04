package com.hackathon.Events.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hackathon.Events.models.UserDetails;

@Repository
public interface UserRepository extends CrudRepository<UserDetails, String>{
	
	UserDetails findByEmail(String email);
	
}
