package com.eu.habbo.messages.incoming.guilds;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.guilds.Guild;
import com.eu.habbo.habbohotel.guilds.GuildMember;
import com.eu.habbo.habbohotel.items.interactions.InteractionGuildFurni;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.guilds.GuildInfoComposer;
import com.eu.habbo.messages.outgoing.rooms.items.FloorItemUpdateComposer;
import com.eu.habbo.plugin.events.guilds.GuildChangedColorsEvent;
import gnu.trove.set.hash.THashSet;

/**
 * Created on 23-11-2014 14:10.
 */
public class GuildChangeColorsEvent extends MessageHandler
{
    @Override
    public void handle() throws Exception
    {
        int guildId = this.packet.readInt();

        Guild guild = Emulator.getGameEnvironment().getGuildManager().getGuild(guildId);

        if(guild != null && guild.getOwnerId() == this.client.getHabbo().getHabboInfo().getId() || this.client.getHabbo().hasPermission("acc_guild_admin"))
        {
            GuildChangedColorsEvent colorsEvent = new GuildChangedColorsEvent(guild, this.packet.readInt(), this.packet.readInt());
            Emulator.getPluginManager().fireEvent(colorsEvent);

            if(colorsEvent.isCancelled())
                return;

            if(guild.getColorOne() != colorsEvent.colorOne || guild.getColorTwo() != colorsEvent.colorTwo)
            {
                guild.setColorOne(colorsEvent.colorOne);
                guild.setColorTwo(colorsEvent.colorTwo);

                Room room = Emulator.getGameEnvironment().getRoomManager().getRoom(guild.getRoomId());

                if(room != null && room.getUserCount() > 0)
                {
                    room.refreshGuild(guild);

                    room.refreshGuildColors(guild);
                }
                guild.needsUpdate = true;
                Emulator.getThreading().run(guild);
            }
        }
    }
}
