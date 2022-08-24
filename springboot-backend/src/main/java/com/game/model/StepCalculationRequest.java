package com.game.model;

import lombok.Data;

@Data
public class StepCalculationRequest {
    private String playerUuid;
    private Integer dice1;
    private Integer dice2;
}
