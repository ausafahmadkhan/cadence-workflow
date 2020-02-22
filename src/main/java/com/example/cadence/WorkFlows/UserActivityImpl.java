package com.example.cadence.WorkFlows;

import com.example.cadence.Persistence.Repository.UserEnrollmentRepository;
import com.example.cadence.Service.UserEnrollmentService;
import com.example.cadence.Service.UserEnrollmentServiceImpl;
import com.example.cadence.Service.UserPaymentService;
import com.uber.cadence.client.ActivityCompletionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserActivityImpl implements UserActivity
{
    @Autowired
    private UserEnrollmentService userEnrollmentService;

    @Autowired
    private UserPaymentService userPaymentService;


    @Override
    public String createUserEnrollment(String userId) {
        System.out.println("User is : " + userId);
        System.out.println("userenroll " + userEnrollmentService);
        userEnrollmentService.createEnrollment(userId);
        return userId;
    }

    @Override
    public Void updateBalance(String userId) {
        System.out.println("Initiating wallet update process");
        userPaymentService.updateUserBalance(userId);
        return null;
    }

}
