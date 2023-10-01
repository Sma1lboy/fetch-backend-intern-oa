package me.jackson.fetchbackendoa.requests;

import lombok.Data;

import java.util.Date;

@Data
public class AddPointsRequest {
    String payer;
    Long points;
    Date timestamp;
}
