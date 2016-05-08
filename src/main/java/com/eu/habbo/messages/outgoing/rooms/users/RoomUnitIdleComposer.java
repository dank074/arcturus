package com.eu.habbo.messages.outgoing.rooms.users;

import com.eu.habbo.habbohotel.rooms.RoomUnit;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

/**
 * Created on 2-8-2015 14:09.
 */
public class RoomUnitIdleComposer extends MessageComposer
{
    private RoomUnit roomUnit;

    public RoomUnitIdleComposer(RoomUnit roomUnit)
    {
        this.roomUnit = roomUnit;
    }

    @Override
    public ServerMessage compose()
    {
        this.response.init(Outgoing.RoomUnitIdleComposer);
        this.response.appendInt32(this.roomUnit.getId());
        this.response.appendBoolean(this.roomUnit.isIdle());
        return this.response;
    }
}
