package com.eu.habbo.messages.incoming.ambassadors;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.rooms.ForwardToRoomComposer;

/**
 * Created on 6-8-2015 22:29.
 */
public class AmbassadorVisitCommandEvent extends MessageHandler
{
    @Override
    public void handle() throws Exception
    {
        if(this.client.getHabbo().hasPermission("acc_ambassador"))
        {
            String username = this.packet.readString();

            Habbo habbo = Emulator.getGameEnvironment().getHabboManager().getHabbo(username);

            if(habbo != null)
            {
                if(habbo.getHabboInfo().getCurrentRoom() != null)
                {
                    this.client.sendResponse(new ForwardToRoomComposer(habbo.getHabboInfo().getCurrentRoom().getId()));
                }
            }
        }
    }
}
