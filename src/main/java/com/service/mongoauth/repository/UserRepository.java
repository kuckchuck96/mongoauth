package com.service.mongoauth.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.service.mongoauth.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	User findByEmail(String email);

	User findByEmailAndPassword(String email, String password);

	@Query("{ isVerified: false, updatedAt: { '$lt': ?0 } }")
	List<User> findByisVerifiedAndUpdatedAt(LocalDateTime lessThan);

}
