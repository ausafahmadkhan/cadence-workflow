package com.example.cadence.Persistence.Repository;

import com.example.cadence.Persistence.Model.UserEnrollmentDAO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEnrollmentRepository extends MongoRepository<UserEnrollmentDAO, String> {
}
