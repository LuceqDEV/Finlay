package org.alexdev.finlay.messages.outgoing.events;

import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class ROOMEVENT_PERMISSION extends MessageComposer {
    private final boolean canCreateEvent;

    public ROOMEVENT_PERMISSION(boolean canCreateEvent) {
        this.canCreateEvent = canCreateEvent;
    }


    @Override
    public void compose(NettyResponse response) {
        response.writeBool(this.canCreateEvent);
    }

    @Override
    public short getHeader() {
        return 367; // "Eo"
    }
}
