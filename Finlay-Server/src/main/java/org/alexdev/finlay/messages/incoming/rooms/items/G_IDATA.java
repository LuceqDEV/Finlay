package org.alexdev.finlay.messages.incoming.rooms.items;

import org.alexdev.finlay.game.item.Item;
import org.alexdev.finlay.game.item.base.ItemBehaviour;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.messages.outgoing.rooms.items.IDATA;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;
import org.apache.commons.lang3.StringUtils;

public class G_IDATA implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        String contents = reader.contents();

        if (!StringUtils.isNumeric(contents)) {
            return;
        }

        int itemId = Integer.parseInt(reader.contents());

        Item item = room.getItemManager().getById(itemId);

        if (item == null) {
            return;
        }

        if (item.hasBehaviour(ItemBehaviour.POST_IT)) {
            String colour = item.getCustomData().substring(0, 6);
            String text = "";

            if (item.getCustomData().length() > 6) {
                text = item.getCustomData().substring(6);
            }

            player.send(new IDATA(item, colour, text));
        } else {
            player.send(new IDATA(item));
        }
    }
}
