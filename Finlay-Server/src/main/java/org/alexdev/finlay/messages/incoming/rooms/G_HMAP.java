package org.alexdev.finlay.messages.incoming.rooms;

import org.alexdev.finlay.game.games.player.GamePlayer;
import org.alexdev.finlay.game.games.snowstorm.SnowStormGame;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.games.FULLGAMESTATUS;
import org.alexdev.finlay.messages.outgoing.rooms.HEIGHTMAP;
import org.alexdev.finlay.messages.outgoing.rooms.OBJECTS_WORLD;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

import java.util.List;

public class G_HMAP implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        GamePlayer gamePlayer = player.getRoomUser().getGamePlayer();

        if (gamePlayer != null && gamePlayer.getGame() instanceof SnowStormGame) {
            SnowStormGame game = (SnowStormGame) gamePlayer.getGame();
            player.send(new HEIGHTMAP(game.getMap().getHeightMap()));
        } else {
            player.send(new HEIGHTMAP(player.getRoomUser().getRoom().getModel()));
        }

        if (gamePlayer != null) {
            player.send(new FULLGAMESTATUS(gamePlayer.getGame()));

            if (gamePlayer.getGame() instanceof SnowStormGame) {
                SnowStormGame game = (SnowStormGame) gamePlayer.getGame();
                player.send(new OBJECTS_WORLD(game.getMap().getCompiledItems()));
            }

            return;
        }
    }
}
