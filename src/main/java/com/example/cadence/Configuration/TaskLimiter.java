package com.example.cadence.Configuration;

import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Semaphore;

@Configuration
public class TaskLimiter
{
    private Semaphore semaphore;

    public TaskLimiter() {
        this.semaphore = new Semaphore(5);
    }

    public boolean isAllowed()
    {
        return semaphore.availablePermits() > 0;
    }

    public void acquire() throws InterruptedException {
        semaphore.acquire();
        System.out.println("Semaphore acquired...");
    }

    public void release()
    {
        semaphore.release();
        System.out.println("Semaphore released...");
    }
}
