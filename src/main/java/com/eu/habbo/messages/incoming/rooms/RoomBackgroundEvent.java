package com.eu.habbo.messages.incoming.rooms;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.rooms.items.FloorItemUpdateComposer;
import com.eu.habbo.plugin.events.furniture.FurnitureRoomTonerEvent;

/**
 * Created on 11-10-2014 22:11.
 */
public class RoomBackgroundEvent extends MessageHandler
{
    @Override
    public void handle() throws Exception
    {
        int itemId = this.packet.readInt();

        Room room = this.client.getHabbo().getHabboInfo().getCurrentRoom();
        if(room == null)
            return;

        if(room.getOwnerId() == this.client.getHabbo().getHabboInfo().getId() || this.client.getHabbo().hasPermission("acc_placefurni"))
        {
            HabboItem item = room.getHabboItem(itemId);

            if(item == null)
                return;

            int hue         = this.packet.readInt();
            int saturation  = this.packet.readInt();
            int brightness  = this.packet.readInt();

            FurnitureRoomTonerEvent event = (FurnitureRoomTonerEvent)Emulator.getPluginManager().fireEvent(new FurnitureRoomTonerEvent(item, this.client.getHabbo(), hue, saturation, brightness));

            if(event.isCancelled())
                return;

            hue        = event.hue        % 255;
            saturation = event.saturation % 255;
            brightness = event.brightness % 255;

            item.setExtradata(item.getExtradata().split(":")[0] + ":" + hue + ":" + saturation + ":" + brightness);
            item.needsUpdate(true);
            Emulator.getThreading().run(item);
            room.updateItem(item);
        }
    }
}
