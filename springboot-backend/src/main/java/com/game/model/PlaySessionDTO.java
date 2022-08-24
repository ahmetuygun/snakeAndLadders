package com.game.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaySessionDTO {

    private String sessionId;
    private List<Player> players;
    private String winnerPlayerId;
    private String currentPlayerId;

}
