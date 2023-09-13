package org.alexdev.finlay.messages.outgoing.rooms.badges;

import org.alexdev.finlay.game.player.PlayerDetails;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class USER_BADGE extends MessageComposer {
    private int instanceId;
    private String currentBadge;
    private boolean showBadge;

    public USER_BADGE(int instanceId, PlayerDetails playerDetails) {
        this.instanceId = instanceId;
        this.currentBadge = playerDetails.getCurrentBadge();
        this.showBadge = playerDetails.getShowBadge();
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.instanceId);

        if (this.showBadge) {
            response.writeString(this.currentBadge);
        }
    }

    @Override
    public short getHeader() {
        return 228; // "Cd"
    }
}
