package com.example.cadence.Service;

public interface UserPaymentService
{
    void updateUserBalance(String userId);

    void reduceUserBalance(String userId);
}
