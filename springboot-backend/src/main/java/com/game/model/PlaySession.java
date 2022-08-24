package com.game.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PlaySession {
    private List<Player> players;
    private String winnerPlayerId;
    private String currentPlayerId;

    public PlaySessionDTO toDto(String session) {

        PlaySessionDTO playSessionDTO = PlaySessionDTO
                .builder()
                .sessionId(session)
                .currentPlayerId(currentPlayerId)
                .players(players)
                .build();
        return playSessionDTO;


    }
}
