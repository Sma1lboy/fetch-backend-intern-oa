package me.jackson.fetchbackendoa.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SpendPointsResponse {
    String payer;
    Long points;
}
