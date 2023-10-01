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

/**
 * The controller layer to help us manage our API.
 */
@RestController()
public class UserController {

    @Autowired
    UserService userService;

    /**
     * Handle the POST request to add points.
     *
     * @param request The request object containing information to add points.
     * @return ResponseEntity with a message or status code.
     */
    @PostMapping("/add")
    public ResponseEntity<String> postAddPoints(@RequestBody AddPointsRequest request) {
        // Check for valid request parameters
        if (request.getPayer() == null) {
            return ResponseEntity.badRequest().body("the payer cannot be empty");
        }
        if (request.getPoints() == null) {
            return ResponseEntity.badRequest().body("the points cannot be empty");
        }
        if (request.getTimestamp() == null) {
            return ResponseEntity.badRequest().body("the timestamp cannot be missing");
        }

        // Call the service to add points
        userService.addPoints(new Transaction(request.getPayer(), request.getPoints(), request.getTimestamp()));

        // Return a success response
        return ResponseEntity.ok().build();
    }

    /**
     * Handle the POST request to spend points.
     *
     * @param request The request object containing information to spend points.
     * @return ResponseEntity with a list of SpendPointsResponse or an error message.
     */
    @PostMapping("/spend")
    public ResponseEntity<?> postSpendPoints(@RequestBody PostSpendPointsRequest request) {
        // Check for valid request parameters
        if (request.getPoints() == null) {
            return ResponseEntity.badRequest().body("the points cannot be empty");
        }

        // Call the service to spend points
        List<Transaction> spentPointsList = userService.spendPoints(request.getPoints());

        if (!spentPointsList.isEmpty()) {
            // Map the transactions to SpendPointsResponse objects
            List<SpendPointsResponse> resp = spentPointsList.stream().map(transaction ->
                    new SpendPointsResponse(transaction.getPayer(), transaction.getPoints())
            ).toList();

            // Return a success response with SpendPointsResponse objects
            return ResponseEntity.ok(resp);
        } else {
            // Return an error response when the user does not have enough points
            return ResponseEntity.badRequest().body("user does not have enough points");
        }
    }

    /**
     * Handle the GET request to retrieve the balance.
     *
     * @return ResponseEntity with a map containing balance information.
     */
    @GetMapping("/balance")
    public ResponseEntity<?> getBalance() {
        // Call the service to get balance information
        Map<String, Long> balanceMap = userService.getBalance();

        // Return the balance information as a success response
        return ResponseEntity.ok(balanceMap);
    }
}
