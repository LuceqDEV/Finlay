package org.alexdev.finlay.messages.incoming.moderation;

import org.alexdev.finlay.game.moderation.cfh.CallForHelp;
import org.alexdev.finlay.game.moderation.cfh.CallForHelpManager;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.moderation.CFH_ACK;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class DELETE_CRY implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        // Retrieve open calls for current user
        CallForHelp cfh = CallForHelpManager.getInstance().getPendingCall(player.getDetails().getId());

        // Make sure call for help exists
        if (cfh == null) {
            return;
        }

        // Delete call for help
        CallForHelpManager.getInstance().deleteCall(cfh);

        // Notify client about the deleted call for help
        player.send(new CFH_ACK(null));
    }
}
