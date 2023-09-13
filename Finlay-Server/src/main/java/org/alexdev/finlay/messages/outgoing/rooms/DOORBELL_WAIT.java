package org.alexdev.finlay.messages.outgoing.rooms;

import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class DOORBELL_WAIT extends MessageComposer {
    private final String username;

    public DOORBELL_WAIT() {
        this.username = null;
    }

    public DOORBELL_WAIT(String username) {
        this.username = username;
    }

    @Override
    public void compose(NettyResponse response) {
        if (this.username != null) {
            response.write(this.username);
        }
    }

    @Override
    public short getHeader() {
        return 91; // "A["
    }
}
