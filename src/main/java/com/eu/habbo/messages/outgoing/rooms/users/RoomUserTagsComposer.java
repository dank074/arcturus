package com.eu.habbo.messages.outgoing.rooms.users;

import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

/**
 * Created on 11-10-2015 10:24.
 */
public class RoomUserTagsComposer extends MessageComposer
{
    private final Habbo habbo;

    public RoomUserTagsComposer(Habbo habbo)
    {
        this.habbo = habbo;
    }

    @Override
    public ServerMessage compose()
    {
        this.response.init(Outgoing.RoomUserTagsComposer);
        this.response.appendInt32(this.habbo.getRoomUnit().getId());
        this.response.appendInt32(this.habbo.getHabboStats().tags.length);

        for(String tag : this.habbo.getHabboStats().tags)
        {
            this.response.appendString(tag);
        }
        return this.response;
    }
}
