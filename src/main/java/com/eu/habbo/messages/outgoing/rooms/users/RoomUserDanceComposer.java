package com.eu.habbo.messages.outgoing.rooms.users;

import com.eu.habbo.habbohotel.rooms.RoomUnit;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

/**
 * Created on 13-9-2014 17:37.
 */
public class RoomUserDanceComposer extends MessageComposer {

    private final RoomUnit roomUnit;

    public RoomUserDanceComposer(RoomUnit roomUnit)
    {
        this.roomUnit = roomUnit;
    }

    @Override
    public ServerMessage compose() {
        this.response.init(Outgoing.RoomUserDanceComposer);
        this.response.appendInt32(this.roomUnit.getId());
        this.response.appendInt32(this.roomUnit.getDanceType().getType());
        return this.response;
    }
}
