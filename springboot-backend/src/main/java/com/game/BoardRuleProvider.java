package com.game;

import java.util.HashMap;
import java.util.Map;

public class BoardRuleProvider {

    private static BoardRuleProvider instance;

    public static BoardRuleProvider get() {

        if (instance == null) {
            instance = new BoardRuleProvider();
        }

        return instance;
    }

    private final Map<Integer, Integer> snakes = new HashMap();
    private final Map<Integer, Integer>  ladders = new HashMap();
    private final Map<String, String> profiles = new HashMap();



    private BoardRuleProvider() {
        initRuleMap();
    }


    public void initRuleMap() {

        ladders.put(2,38);
        ladders.put(7,14);
        ladders.put(8,31);
        ladders.put(15,26);
        ladders.put(21,42);
        ladders.put(28,84);
        ladders.put(36,44);
        ladders.put(51,67);
        ladders.put(71,91);
        ladders.put(78,98);
        ladders.put(87,94);
        snakes.put(16,6);
        snakes.put(46,25);
        snakes.put(49,11);
        snakes.put(62,19);
        snakes.put(64,60);
        snakes.put(74,53);
        snakes.put(89,68);
        snakes.put(92,88);
        snakes.put(95,75);
        snakes.put(99,80);


        profiles.put("Player 1","square");
        profiles.put("Player 2","hexagon");
        profiles.put("Player 3","circle");
        profiles.put("Player 4","triangle");

    }

    public Map<Integer, Integer> getSnakes() {
        return snakes;
    }

    public Map<Integer, Integer> getLadders() {
        return ladders;
    }

    public Map<String, String> getProfiles() {
        return profiles;
    }
}
