package com.eu.habbo.messages.incoming.catalog.marketplace;

import com.eu.habbo.habbohotel.catalog.marketplace.MarketPlace;
import com.eu.habbo.messages.incoming.MessageHandler;

/**
 * Created on 2-11-2014 10:41.
 */
public class BuyItemEvent extends MessageHandler
{
    @Override
    public void handle() throws Exception
    {
        int offerId = this.packet.readInt();

        MarketPlace.buyItem(offerId, this.client);
    }
}
