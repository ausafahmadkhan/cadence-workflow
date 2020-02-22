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

//    @Autowired
//    public UserEnrollmentServiceImpl(UserEnrollmentRepository userEnrollmentRepository)
//    {
//        this.userEnrollmentRepository = userEnrollmentRepository;
//        System.out.println("Instantiated : " + this.userEnrollmentRepository);
//        System.out.println("Instantiated member : " + userEnrollmentRepository);
//    }

    @Override
    public void createEnrollment(String userId)
    {
        System.out.println("Instantiated member : " + userEnrollmentRepository);
        System.out.println("here");
        String enrollmentId = UUID.randomUUID().toString();
        System.out.println("enrollment id : " + enrollmentId);
        UserEnrollmentDAO userEnrollmentDAO = new UserEnrollmentDAO(userId, enrollmentId);
        System.out.println(userEnrollmentDAO + "DAO");
        System.out.println("repo :" + userEnrollmentRepository);
        System.out.println("this.repo :" + this.userEnrollmentRepository);
        userEnrollmentRepository.save(userEnrollmentDAO);
        System.out.println("Enrollment created for user : " +  userId);
    }
}
