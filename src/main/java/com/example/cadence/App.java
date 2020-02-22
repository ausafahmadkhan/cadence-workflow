package com.example.cadence;

import com.example.cadence.WorkFlows.UserActivityImpl;
import com.example.cadence.WorkFlows.UserWorkFlowImpl;
import com.uber.cadence.worker.Worker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App
{
    public static void main(String[] args)
    {
        SpringApplication.run(App.class, args);
    }
}
