package com.hoaxify.ws.repository;

import com.hoaxify.ws.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String username);
	
}
