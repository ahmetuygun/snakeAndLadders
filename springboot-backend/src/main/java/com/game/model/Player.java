package com.game.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    private String uuid;
    private int turn;
    private int currentBox;
    private String shape;
    private String name;
    private Boolean itsTurn;

    public Player(String uuid) {
        this.uuid = uuid;
    }

    public Player( int turn,String name, String shape) {
        this.uuid = UUID.randomUUID().toString();
        this.turn = turn;
        this.shape = shape;
        this.name = name;
        this.currentBox = 1;
    }

}
