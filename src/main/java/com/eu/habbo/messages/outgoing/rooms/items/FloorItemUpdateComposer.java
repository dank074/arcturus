package com.eu.habbo.messages.outgoing.rooms.items;

import com.eu.habbo.habbohotel.items.interactions.InteractionGift;
import com.eu.habbo.habbohotel.items.interactions.InteractionMusicDisc;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

/**
 * Created on 20-9-2014 21:16.
 */
public class FloorItemUpdateComposer extends MessageComposer {

    private final HabboItem item;

    public FloorItemUpdateComposer(HabboItem item)
    {
        this.item = item;
    }

    @Override
    public ServerMessage compose() {
        this.response.init(Outgoing.FloorItemUpdateComposer);
        this.item.serializeFloorData(this.response);
        this.response.appendInt32(this.item instanceof InteractionGift ? ((((InteractionGift) this.item).getColorId() * 1000) + ((InteractionGift) this.item).getRibbonId()) : (this.item instanceof InteractionMusicDisc ? ((InteractionMusicDisc)this.item).getSongId() : 1));
        this.item.serializeExtradata(this.response);
        this.response.appendInt32(-1);
        this.response.appendInt32(this.item.getBaseItem().getStateCount() > 1 ? 1 : 0);
        this.response.appendInt32(this.item.getUserId());
        return this.response;
    }
}
