package com.game.service;

import com.game.model.*;
import com.game.BoardRuleProvider;
import com.game.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class PlayService {

    //All browser session are hold in this HashMap, so multiple player group can play on multiple session.
    private Map<String, PlaySession> playSessionMap = new ConcurrentHashMap<>();

    public PlaySessionDTO initializePlayerSession(String sessionId) {

        AtomicInteger turn = new AtomicInteger();
        turn.getAndIncrement(); // setting player's turn // start from 1

        // initialize players
        List<Player>  playerList =  BoardRuleProvider
                .get()
                .getProfiles()
                .entrySet()
                .stream()
                .map( e ->  new Player(turn.getAndIncrement(),e.getKey(),e.getValue())).collect(Collectors.toList());

        //Setting first player
        playerList.stream().filter( item -> item.getTurn() == 1).forEach( item -> item.setItsTurn(Boolean.TRUE));

        PlaySession playSession = PlaySession
                .builder()
                .players(playerList)
                .currentPlayerId(playerList.get(0).getUuid())
                .build();
        playSessionMap.put(sessionId, playSession);
        return playSession.toDto(sessionId);
    }

    public CalculationDto calculateStep(String sessionId, StepCalculationRequest stepCalculationRequest) {

        CalculationDto calculationDto = new CalculationDto();
        List<Player> playerList = null;

        //find session by sessionKey
        if (playSessionMap.containsKey(sessionId)) {

            String playerUuid = stepCalculationRequest.getPlayerUuid();
            playerList = playSessionMap.get(sessionId).getPlayers();

            //Find current player by player uuid, otherwise this is an exception
            Player currentPlayer = playerList.stream().filter(item -> item.getUuid().equals(playerUuid)).findFirst().orElseThrow(() -> new ResourceNotFoundException("Cannot find the player :" + playerUuid));

            int currentBox = currentPlayer.getCurrentBox();
            int stepAmount = stepCalculationRequest.getDice1() + stepCalculationRequest.getDice2();
            int targetBox = currentBox + stepAmount;
            targetBox = checkSnacks(targetBox); // check is there any snack on way
            targetBox = checkLadders(targetBox); // check is there any letter on way

            if(targetBox==100){
                calculationDto.setWinner(true);
                playSessionMap.get(sessionId).setWinnerPlayerId(playerUuid);
            }if (targetBox >100){  // if the player overshot, then go back
                targetBox = currentBox - stepAmount;
            }

            int finalTargetBox = targetBox;
            playerList.stream().filter(item -> item.getUuid().equals(playerUuid)).forEach(item -> item.setCurrentBox(finalTargetBox));

            // if dices are double, the next player is current player
            if(stepCalculationRequest.getDice1() != stepCalculationRequest.getDice2()){
                int nextPlayerTurn = (currentPlayer.getTurn() % 4) + 1 ;
                String nextPlayerUuid  = playerList.stream().filter(item -> item.getTurn() == nextPlayerTurn).map(item ->item.getUuid()).findFirst().get();
                calculationDto.setNextPlayerUuid(nextPlayerUuid);
            }else{
                calculationDto.setNextPlayerUuid(currentPlayer.getUuid());
            }
            calculationDto.setPlayer(currentPlayer);
        } else{
          throw  new  ResourceNotFoundException("Session couldn't found");
        }
        return calculationDto;
    }

    private int checkLadders(int targetBox) {
        return BoardRuleProvider.get().getLadders().getOrDefault(targetBox, targetBox);
    }

    private int checkSnacks(int targetBox) {
        return BoardRuleProvider.get().getSnakes().getOrDefault(targetBox, targetBox);
    }

    public PlaySessionDTO createSession(String sessionId) {
        return initializePlayerSession(sessionId);
    }

    private String getUuid() {
        return UUID.randomUUID().toString();
    }

}
