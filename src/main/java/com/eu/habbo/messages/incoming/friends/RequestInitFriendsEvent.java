package com.eu.habbo.messages.incoming.friends;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.friends.FriendsComposer;
import com.eu.habbo.messages.outgoing.friends.MessengerInitComposer;

import java.util.ArrayList;

/**
 * Created on 27-7-2015 12:47.
 */
public class RequestInitFriendsEvent extends MessageHandler
{
    @Override
    public void handle() throws Exception
    {
        ArrayList<ServerMessage> messages = new ArrayList<ServerMessage>();
//
//        messages.add(new MessengerInitComposer(this.client.getHabbo()).compose());
//        messages.add(new FriendsComposer(this.client.getHabbo()).compose());
        this.client.sendResponses(messages);
    }
}
