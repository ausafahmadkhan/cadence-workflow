package com.example.cadence.Service;

import com.example.cadence.Persistence.Model.UserWalletDAO;
import com.example.cadence.Persistence.Repository.UserWalletRepository;
import com.uber.cadence.activity.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class UserPaymentServiceImpl implements UserPaymentService
{
    @Autowired
    private UserWalletRepository userWalletRepository;

    @Autowired
    private Jedis jedis;


    @Override
    public void reduceUserBalance(String userId) {
        try {
            UserWalletDAO userWalletDAO = userWalletRepository.findById(userId)
                    .orElseThrow(IllegalArgumentException::new);
            double balance = Double.parseDouble(jedis.get("User_"+ userId + "_balance"));
            userWalletDAO.setWalletBalance(balance);
            userWalletRepository.save(userWalletDAO);
            System.out.println("Compensated user balance to " + userWalletDAO.getWalletBalance());
        }
        catch (Exception e)
        {
            System.out.println("Catch //compensated");
        }
    }

    @Override
    public void updateUserBalance(String userId)
    {
        UserWalletDAO userWalletDAO = userWalletRepository.findById(userId)
                .orElseThrow(IllegalArgumentException::new);

        int curAttempt = Activity.getTask().getAttempt();
        if (curAttempt == 0)
            jedis.set("User_"+ userId + "_balance", userWalletDAO.getWalletBalance()+"");
        System.out.println("Current Attempt : " + curAttempt);
        userWalletDAO.setWalletBalance(userWalletDAO.getWalletBalance() - 200);
        userWalletRepository.save(userWalletDAO);
        System.out.println("Updated user balance to " + userWalletDAO.getWalletBalance());

    }
}
