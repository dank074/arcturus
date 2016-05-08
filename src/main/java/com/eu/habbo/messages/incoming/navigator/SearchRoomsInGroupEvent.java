package com.eu.habbo.messages.incoming.navigator;

import com.eu.habbo.Emulator;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.navigator.PrivateRoomsComposer;

/**
 * Created on 22-11-2014 10:37.
 */
public class SearchRoomsInGroupEvent extends MessageHandler
{
    @Override
    public void handle() throws Exception
    {
        this.client.sendResponse(new PrivateRoomsComposer(Emulator.getGameEnvironment().getRoomManager().getRoomsInGroup(this.client.getHabbo())));
    }
}
