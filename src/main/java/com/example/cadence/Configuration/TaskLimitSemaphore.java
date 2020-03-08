package com.example.cadence.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class TaskLimitSemaphore
{
    private ExecutorService executor;
    private Semaphore semaphore;

    public TaskLimitSemaphore()
    {
        this.executor = Executors.newCachedThreadPool();
        this.semaphore = new Semaphore(5, true);
    }

    public <T> Future<T> execute(final Callable<T> task) throws Exception {
        semaphore.acquire();
        System.out.println("Semaphore acquired...");

        return executor.submit(() -> {
            try {
                return task.call();
            } finally {
                semaphore.release();
                System.out.println("Semaphore released...");
            }
        });
    }
}
