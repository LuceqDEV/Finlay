package org.alexdev.finlay.game.games.gamehalls;

import org.alexdev.finlay.game.item.Item;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.game.games.triggers.GameTrigger;

import java.util.List;

public class GamePoker extends GamehallGame {
    public GamePoker(List<int[]> kvp) {
        super(kvp);
    }

    @Override
    public void gameStart() {

    }

    @Override
    public void gameStop() { }

    @Override
    public void handleCommand(Player player, Room room, Item item, String command, String[] args) {
        GameTrigger trigger = (GameTrigger) item.getDefinition().getInteractionType().getTrigger();

        if (command.equals("CLOSE")) {
            trigger.onEntityLeave(player, player.getRoomUser(), item);
            return;
        }
    }

    @Override
    public int getMaximumPeopleRequired() {
        return 4;
    }

    @Override
    public int getMinimumPeopleRequired() {
        return 2;
    }

    @Override
    public String getGameFuseType() {
        return "Poker";
    }
}
