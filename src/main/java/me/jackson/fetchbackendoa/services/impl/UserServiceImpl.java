package me.jackson.fetchbackendoa.services.impl;

import me.jackson.fetchbackendoa.models.Transaction;
import me.jackson.fetchbackendoa.models.User;
import me.jackson.fetchbackendoa.services.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * I removed the DAO layer(repositories), because we already contact to the actual data.
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public void addPoints(Transaction transaction) {
        String payer = transaction.getPayer();
        Map<String, Long> pointsMap = User.payerPointsCounter;
        /*
          Add to total points.
         */
        User.totalPoints += transaction.getPoints();
        /*
        put to spending queue base on timestamp
         */
        User.pointQueue.offer(transaction);
        /*
        put into trasaction history list
         */
        User.transactionsHistory.add(transaction);
        /*
        total point counter
         */
        pointsMap.put(payer, pointsMap.getOrDefault(payer, 0L) + transaction.getPoints());
    }

    /**
     * Spending points from users spending queue, from old to new
     * @param points
     * @return
     */
    @Override
    public List<Transaction> spendPoints(long points) {

        List<Transaction> res = new ArrayList<>();
        if(points > User.totalPoints) {
            return res;
        }
        /*
        reduce from total points
         */
        User.totalPoints -= points;

        PriorityQueue<Transaction>  q = User.pointQueue;
        Map<String, Long> pointsMap = User.payerPointsCounter;


        while(points > 0) {
            Transaction transaction = q.poll();

            assert transaction != null;
            if(transaction.getPoints() > points) {
                transaction.setPoints(transaction.getPoints() - points );
                //add back into queue
                q.offer(transaction);
                //add into spent history list
                res.add(new Transaction(transaction.getPayer(), -points));
                //reduce total point of this payer
                pointsMap.put(transaction.getPayer(), pointsMap.get(transaction.getPayer()) - points);

                points = 0;
            } else {
                pointsMap.put(transaction.getPayer(), pointsMap.get(transaction.getPayer()) - transaction.getPoints());

                points -= transaction.getPoints();
                transaction.setPoints(-transaction.getPoints());
                res.add(transaction);
            }
        }
        return res;
    }

    @Override
    public Map<String, Long> getBalance() {
        return User.payerPointsCounter;
    }
}
