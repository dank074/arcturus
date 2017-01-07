package com.eu.habbo.messages.outgoing.rooms;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.guilds.Guild;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

public class RoomDataComposer extends MessageComposer
{

    private final Room room;
    private final Habbo habbo;
    private final boolean publicRoom;
    private final boolean unknown;

    public RoomDataComposer(Room room, Habbo habbo, boolean boolA, boolean boolB)
    {
        this.room = room;
        this.habbo = habbo;
        this.publicRoom = boolA;
        this.unknown = boolB;
    }

    @Override
    public ServerMessage compose()
    {
        this.response.init(Outgoing.RoomDataComposer);
        this.response.appendBoolean(this.unknown);
        this.response.appendInt32(this.room.getId());
        this.response.appendString(this.room.getName());
        if (this.room.isPublicRoom())
        {
            this.response.appendInt32(0);
            this.response.appendString("");
        } else
        {
            this.response.appendInt32(this.room.getOwnerId());
            this.response.appendString(this.room.getOwnerName());
        }
        this.response.appendInt32(this.room.getState().getState());
        this.response.appendInt32(this.room.getUserCount());
        this.response.appendInt32(this.room.getUsersMax());
        this.response.appendString(this.room.getDescription());
        this.response.appendInt32(0);
        this.response.appendInt32(2);
        this.response.appendInt32(this.room.getScore());
        this.response.appendInt32(this.room.getCategory());
        String[] tags = this.room.getTags().split(";");
        this.response.appendInt32(tags.length);
        for(String s : tags)
        {
            this.response.appendString(s);
        }

        int base = 8 | 16;

        if(this.room.getGuildId() > 0)
        {
            base = base | 2;
        }

        if(!this.room.isPublicRoom())
        {
            base = base | 8;
        }

        if(this.room.isPromoted())
        {
            base = base | 4;
        }

        this.response.appendInt32(base);

        if(this.room.getGuildId() > 0)
        {
            Guild g = Emulator.getGameEnvironment().getGuildManager().getGuild(this.room.getGuildId());
            if (g != null)
            {
                this.response.appendInt32(g.getId());
                this.response.appendString(g.getName());
                this.response.appendString(g.getBadge());
            }
            else
            {
                this.response.appendInt32(0);
                this.response.appendString("");
                this.response.appendString("");
            }
        }

        if(this.room.isPromoted())
        {
            this.response.appendString(this.room.getPromotion().getTitle());
            this.response.appendString(this.room.getPromotion().getDescription());
            this.response.appendInt32((this.room.getPromotion().getEndTimestamp() - Emulator.getIntUnixTimestamp()) / 60);
        }

        this.response.appendBoolean(this.publicRoom);
        this.response.appendBoolean(this.room.isStaffPromotedRoom()); //staffpicked
        this.response.appendBoolean(this.room.isPublicRoom()); //ispublicroom
        this.response.appendBoolean(this.room.isMuted()); //isroommuted

        this.response.appendInt32(this.room.getMuteOption());
        this.response.appendInt32(this.room.getKickOption());
        this.response.appendInt32(this.room.getBanOption());

        this.response.appendBoolean(this.room.hasRights(this.habbo)); //mute all button

        this.response.appendInt32(this.room.getChatMode());
        this.response.appendInt32(this.room.getChatWeight());
        this.response.appendInt32(this.room.getChatSpeed());
        this.response.appendInt32(this.room.getChatDistance());
        this.response.appendInt32(this.room.getChatProtection());


        return this.response;
    }
}