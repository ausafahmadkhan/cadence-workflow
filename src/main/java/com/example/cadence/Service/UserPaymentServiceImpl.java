package com.example.cadence.Service;

import com.example.cadence.Persistence.Model.UserWalletDAO;
import com.example.cadence.Persistence.Repository.UserWalletRepository;
import com.uber.cadence.workflow.Workflow;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPaymentServiceImpl implements UserPaymentService
{
    @Autowired
    private UserWalletRepository userWalletRepository;

    @Override
    public void updateUserBalance(String userId)
    {
        try {
            UserWalletDAO userWalletDAO = userWalletRepository.findById(userId)
                    .orElseThrow(IllegalArgumentException::new);
            userWalletDAO.setWalletBalance(userWalletDAO.getWalletBalance() - 200);
            userWalletRepository.save(userWalletDAO);
            System.out.println("Updated user balance to " + userWalletDAO.getWalletBalance());
        }
        catch (Exception e)
        {
            System.out.println("Illegal arg");
        }
    }
}
