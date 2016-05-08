package com.eu.habbo.threading.runnables;

import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.outgoing.rooms.users.RoomUserHandItemComposer;
import com.eu.habbo.messages.outgoing.rooms.users.RoomUserReceivedHandItemComposer;

/**
 * Created on 2-8-2015 16:09.
 */
public class HabboGiveHandItemToHabbo implements Runnable
{
    private Habbo target;
    private Habbo from;
    private int handItem;

    public HabboGiveHandItemToHabbo(Habbo from, Habbo target)
    {
        this.target = target;
        this.from = from;
    }

    @Override
    public void run()
    {
        if(this.from.getHabboInfo().getCurrentRoom() == null || this.target.getHabboInfo().getCurrentRoom() == null)
            return;

        if(this.from.getHabboInfo().getCurrentRoom() != this.target.getHabboInfo().getCurrentRoom())
            return;

        int itemId = this.from.getRoomUnit().getHandItem();

        if(itemId > 0)
        {
            this.target.getRoomUnit().setHandItem(itemId);
            this.target.getHabboInfo().getCurrentRoom().sendComposer(new RoomUserHandItemComposer(this.target.getRoomUnit()).compose());
            this.from.getRoomUnit().setHandItem(0);
            this.from.getHabboInfo().getCurrentRoom().sendComposer(new RoomUserHandItemComposer(this.from.getRoomUnit()).compose());
            this.target.getClient().sendResponse(new RoomUserReceivedHandItemComposer(this.from.getRoomUnit(), this.target.getRoomUnit().getHandItem()));
        }
    }
}
