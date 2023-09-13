package org.alexdev.finlay.messages.incoming.rooms.moderation;

import org.alexdev.finlay.dao.mysql.RoomRightsDao;
import org.alexdev.finlay.game.fuserights.Fuseright;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.player.PlayerManager;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class REMOVERIGHTS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        Room room = player.getRoomUser().getRoom();

        if (!room.isOwner(player.getDetails().getId()) && !player.hasFuse(Fuseright.ANY_ROOM_CONTROLLER)) {
            return;
        }

        Player target = PlayerManager.getInstance().getPlayerByName(reader.contents());

        if (target == null) {
            return;
        }

        Integer userId = target.getDetails().getId();

        if (!room.getRights().contains(userId)) {
            return;
        }

        room.getRights().remove(userId);
        room.refreshRights(target);

        RoomRightsDao.removeRights(target.getDetails(), room.getData());
    }
}
