package org.alexdev.finlay.game.games.utils;

import org.alexdev.finlay.game.GameScheduler;
import org.alexdev.finlay.game.games.Game;
import org.alexdev.finlay.game.games.battleball.enums.BattleBallPlayerState;
import org.alexdev.finlay.game.games.battleball.objects.PlayerUpdateObject;
import org.alexdev.finlay.game.games.enums.GameState;
import org.alexdev.finlay.game.games.player.GamePlayer;

import java.util.concurrent.TimeUnit;

public class PowerUpUtil {
    public static void stunPlayer(Game game, GamePlayer gamePlayer, BattleBallPlayerState state) {
        gamePlayer.getPlayer().getRoomUser().setWalkingAllowed(false);
        gamePlayer.getPlayer().getRoomUser().stopWalking();

        gamePlayer.setPlayerState(state);
        game.addObjectToQueue(new PlayerUpdateObject(gamePlayer));

        // Restore player 5 seconds later
        GameScheduler.getInstance().getService().schedule(()-> {
            if (game.getGameState() != GameState.ENDED) {
                gamePlayer.getPlayer().getRoomUser().setWalkingAllowed(true);
            }

            gamePlayer.setPlayerState(BattleBallPlayerState.NORMAL);
            game.addObjectToQueue(new PlayerUpdateObject(gamePlayer));
        }, 5, TimeUnit.SECONDS);
    }

}
