package com.example.cadence.WorkFlows;

import com.example.cadence.Service.UserEnrollmentService;
import com.example.cadence.Service.UserPaymentService;
import com.example.cadence.Utils.JedisWrapper;
import com.uber.cadence.activity.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class UserActivityImpl implements UserActivity
{

    @Autowired
    private UserEnrollmentService userEnrollmentService;

    @Autowired
    private UserPaymentService userPaymentService;


    @Override
    public String createUserEnrollment(String userId) {
        userEnrollmentService.createEnrollment(userId);
        return userId;
    }

    @Override
    public Void compensateUserEnrollment(String userId) {
        userEnrollmentService.deleteEnrollment(userId);
        System.out.println("Compensated");
        return null;
    }

    @Override
    public Void updateBalance(String userId) {
        userPaymentService.updateUserBalance(userId);
        throw new RuntimeException("runtime error");
//        return null;
    }

    @Override
    public Void compensateBalanceUpdate(String userId) {
        userPaymentService.reduceUserBalance(userId);
        return null;
    }

}
