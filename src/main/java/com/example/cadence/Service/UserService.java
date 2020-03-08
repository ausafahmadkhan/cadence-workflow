package com.example.cadence.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface UserService
{
   String enrollStudent(String userId) throws Exception;
}
