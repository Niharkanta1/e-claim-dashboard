package com.eclaims.app.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eclaims.app.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String userName);

}
