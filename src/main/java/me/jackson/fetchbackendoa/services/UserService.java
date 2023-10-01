package me.jackson.fetchbackendoa.services;

import me.jackson.fetchbackendoa.models.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface UserService {
    void addPoints(Transaction transaction);
    List<Transaction> spendPoints(long points);
    Map<String, Long> getBalance();
}
