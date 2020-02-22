package com.example.cadence.Persistence.Repository;

import com.example.cadence.Persistence.Model.UserWalletDAO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWalletRepository extends MongoRepository<UserWalletDAO, String> {
}
