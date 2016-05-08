package com.eu.habbo.messages.incoming.users;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.HabboGender;
import com.eu.habbo.habbohotel.users.inventory.WardrobeComponent;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.plugin.events.users.UserSavedWardrobeEvent;

/**
 * Created on 13-9-2014 19:04.
 */
public class SaveWardrobeEvent extends MessageHandler
{
    @Override
    public void handle() throws Exception
    {
        int slotId = this.packet.readInt();
        String look = this.packet.readString();
        String gender = this.packet.readString();

        WardrobeComponent.WardrobeItem wardrobeItem;
        if(this.client.getHabbo().getHabboInventory().getWardrobeComponent().getLooks().containsKey(slotId))
        {
            wardrobeItem = this.client.getHabbo().getHabboInventory().getWardrobeComponent().getLooks().get(slotId);
            wardrobeItem.setGender(HabboGender.valueOf(gender));
            wardrobeItem.setLook(look);
            wardrobeItem.setNeedsUpdate(true);
        }
        else
        {
            wardrobeItem = this.client.getHabbo().getHabboInventory().getWardrobeComponent().createLook(this.client.getHabbo(), slotId, look);
            wardrobeItem.setNeedsInsert(true);
        }

        UserSavedWardrobeEvent wardrobeEvent = new UserSavedWardrobeEvent(this.client.getHabbo(), wardrobeItem);
        Emulator.getPluginManager().fireEvent(wardrobeEvent);

        Emulator.getThreading().run(wardrobeItem);
    }
}
