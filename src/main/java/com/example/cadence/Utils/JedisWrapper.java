package com.example.cadence.Utils;

import com.example.cadence.Persistence.Repository.UserWalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class JedisWrapper
{
    @Bean
    public Jedis getJedisClient()
    {
        return new Jedis();
    }
}
