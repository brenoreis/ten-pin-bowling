package com.breno.tenpinbowling;

import com.breno.tenpinbowling.service.Game;

/**
 * Created by breno.pinto on 12/5/17.
 */
public class TenPinBowling {

    public static void main(String[] args) {
        Game game = new Game();
        System.out.println(game.calculateScore(args));
    }

}
