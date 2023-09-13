package org.alexdev.finlay.game.commands.registered;

import org.alexdev.finlay.dao.mysql.PlayerDao;
import org.alexdev.finlay.game.commands.Command;
import org.alexdev.finlay.game.entity.Entity;
import org.alexdev.finlay.game.entity.EntityType;
import org.alexdev.finlay.game.fuserights.Fuseright;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.rooms.user.FIGURE_CHANGE;
import org.alexdev.finlay.util.StringUtil;

public class ChangeMottoCommand extends Command {
    @Override
    public void addPermissions() {
        this.permissions.add(Fuseright.DEFAULT);
    }

    @Override
    public void addArguments() { }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) entity;

        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        // Filter out possible packet injection attacks
        String motto;

        if (args.length > 0) {
            motto = StringUtil.filterInput(String.join(" ", args), true);
        } else {
            motto = "";
        }

        // Update motto
        player.getDetails().setMotto(motto);
        PlayerDao.saveMotto(player.getDetails());

        // Notify room of changed motto
        player.getRoomUser().getRoom().send(new FIGURE_CHANGE(player.getRoomUser().getInstanceId(), player.getDetails()));
    }

    @Override
    public String getDescription() {
        return "Change your motto";
    }
}
