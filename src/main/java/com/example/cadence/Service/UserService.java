package com.example.cadence.Service;

import java.util.concurrent.CompletableFuture;

public interface UserService
{
    CompletableFuture<String> enrollStudent(String userId);
}
