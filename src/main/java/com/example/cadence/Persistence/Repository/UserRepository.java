package com.example.cadence.Persistence.Repository;

import com.example.cadence.Persistence.Model.UserDAO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserDAO, String> {
}
