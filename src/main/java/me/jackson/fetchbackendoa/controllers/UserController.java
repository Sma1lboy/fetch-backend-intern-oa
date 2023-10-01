package me.jackson.fetchbackendoa.controllers;

import me.jackson.fetchbackendoa.models.Transaction;
import me.jackson.fetchbackendoa.requests.AddPointsRequest;
import me.jackson.fetchbackendoa.requests.PostSpendPointsRequest;
import me.jackson.fetchbackendoa.requests.SpendPointsResponse;
import me.jackson.fetchbackendoa.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController()
public class UserController {

    @Autowired
    UserService userService;
    @PostMapping("/add")
    public ResponseEntity<?> postAddPoints(@RequestBody AddPointsRequest request) {
        if(request.getPayer() == null) {
            return ResponseEntity.badRequest().body("the payer cannot be empty");
        }
        if(request.getPoints() == null) {
            return ResponseEntity.badRequest().body("the points cannot be empty");
        }
        if(request.getTimestamp() == null) {
            return ResponseEntity.badRequest().body("the timestamp cannot be missing");
        }
        userService.addPoints(new Transaction(request.getPayer(), request.getPoints(), request.getTimestamp()));
        return ResponseEntity.ok().build();
    }
    @PostMapping("/spend")
    public ResponseEntity<?> postSpendPoints(@RequestBody PostSpendPointsRequest request) {
        if(request.getPoints() == null) {
            return ResponseEntity.badRequest().body("the points cannot be empty");
        }
        List<Transaction> spentPointsList = userService.spendPoints(request.getPoints());

        if(!spentPointsList.isEmpty()) {
            List<SpendPointsResponse> resp = spentPointsList.stream().map(transaction ->
              new SpendPointsResponse(transaction.getPayer(), transaction.getPoints())
            ).toList();
            return ResponseEntity.ok(resp);
        } else {
            return ResponseEntity.badRequest().body("user does not have enough points");
        }
    }
    @GetMapping("/balance")
    public ResponseEntity<?> getBalance() {
        Map<String, Long> balanceMap = userService.getBalance();
        return ResponseEntity.ok(balanceMap);
    }
}
