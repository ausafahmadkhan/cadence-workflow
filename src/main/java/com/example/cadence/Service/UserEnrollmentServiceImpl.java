package com.example.cadence.Service;

import com.example.cadence.Persistence.Model.UserEnrollmentDAO;
import com.example.cadence.Persistence.Repository.UserEnrollmentRepository;
import com.uber.cadence.workflow.Workflow;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserEnrollmentServiceImpl implements UserEnrollmentService
{
    @Autowired
    private UserEnrollmentRepository userEnrollmentRepository;


    @Override
    public void createEnrollment(String userId)
    {
        String enrollmentId = UUID.randomUUID().toString();
        UserEnrollmentDAO userEnrollmentDAO = new UserEnrollmentDAO(userId, enrollmentId);
        userEnrollmentRepository.save(userEnrollmentDAO);
        System.out.println("Enrollment created for user : " +  userId);
    }
}
