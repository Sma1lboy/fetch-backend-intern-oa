package me.jackson.fetchbackendoa.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    String payer;
    Long points;
    Date timestamp;
    public Transaction(String payer, Long points) {
        this.payer = payer;
        this.points = points;
    }
}
