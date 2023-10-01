package me.jackson.fetchbackendoa.models;

import java.util.*;

/**
 * The mock user that helps us to test.
 */
public class User {


    /**
     * keep tracking the transaction history.
     */
    public static List<Transaction> transactionsHistory;

    public static Long totalPoints;
    /**
     * Spending the points base on timestamp matter
     */
    public static PriorityQueue<Transaction> pointQueue;
    public static Map<String, Long> payerPointsCounter;
    static {
        totalPoints = 0L;
        transactionsHistory = new ArrayList<>();
        payerPointsCounter = new HashMap<>();
        //sort base on timestamp
        pointQueue = new PriorityQueue<>(Comparator.comparing(a -> a.timestamp));
}
}
