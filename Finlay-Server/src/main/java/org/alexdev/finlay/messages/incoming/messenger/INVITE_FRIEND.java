package org.alexdev.finlay.messages.incoming.messenger;

import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.player.PlayerManager;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.messages.outgoing.messenger.INSTANT_MESSAGE_INVITATION;
import org.alexdev.finlay.messages.outgoing.messenger.INVITATION_ERROR;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;
import org.alexdev.finlay.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class INVITE_FRIEND implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }


        int users = reader.readInt();

        List<Player> friends = new ArrayList<>();

        for (int i = 0; i < users; i++) {
            int userId = reader.readInt();

            if (!player.getMessenger().hasFriend(userId)) {
                break;
            }

            Player friend = PlayerManager.getInstance().getPlayerById(userId);

            if (friend == null) {
                player.send(new INVITATION_ERROR());
                return;
            }

            friends.add(friend);
        }

        String message = StringUtil.filterInput(reader.readString(), false);

        for (Player friend : friends) {
            friend.send(new INSTANT_MESSAGE_INVITATION(player.getDetails().getId(), message));
        }
    }
}
