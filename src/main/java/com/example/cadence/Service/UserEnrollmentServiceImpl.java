package com.example.cadence.Service;

import com.example.cadence.Persistence.Model.UserEnrollmentDAO;
import com.example.cadence.Persistence.Repository.UserEnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserEnrollmentServiceImpl implements UserEnrollmentService
{
    @Autowired
    private UserEnrollmentRepository userEnrollmentRepository;

    @Override
    public void deleteEnrollment(String userId)
    {
        try {
            userEnrollmentRepository.deleteById(userId);
            System.out.println("Enrollment deleted");
        }
        catch (Exception e)
        {
            System.out.println("caught compensated");
        }
    }

    @Override
    public void createEnrollment(String userId)
    {
        String enrollmentId = UUID.randomUUID().toString();
        UserEnrollmentDAO userEnrollmentDAO = new UserEnrollmentDAO(userId, enrollmentId);
        userEnrollmentRepository.save(userEnrollmentDAO);
        System.out.println("Enrollment created for user : " +  userId);
    }
}
