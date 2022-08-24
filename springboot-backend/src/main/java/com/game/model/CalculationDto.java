package com.game.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalculationDto {
    private Player player;
    public Boolean winner;
    public String nextPlayerUuid;
}
