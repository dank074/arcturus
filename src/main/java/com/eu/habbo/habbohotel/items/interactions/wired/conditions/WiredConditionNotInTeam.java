package com.eu.habbo.habbohotel.items.interactions.wired.conditions;

import com.eu.habbo.habbohotel.games.GameTeamColors;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.InteractionWiredCondition;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomUnit;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.wired.WiredConditionType;
import com.eu.habbo.messages.ClientMessage;
import com.eu.habbo.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created on 13-12-2014 20:54.
 */
public class WiredConditionNotInTeam extends InteractionWiredCondition
{
    public static final WiredConditionType type = WiredConditionType.NOT_ACTOR_IN_TEAM;

    private GameTeamColors teamColor = GameTeamColors.RED;

    public WiredConditionNotInTeam(ResultSet set, Item baseItem) throws SQLException
    {
        super(set, baseItem);
    }

    public WiredConditionNotInTeam(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells)
    {
        super(id, userId, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public boolean execute(RoomUnit roomUnit, Room room, Object[] stuff)
    {
        Habbo habbo = room.getHabbo(roomUnit);

        if(habbo != null)
        {
            if(habbo.getHabboInfo().getGamePlayer() != null)
            {
                if(habbo.getHabboInfo().getGamePlayer().getTeamColor().equals(this.teamColor))
                    return false;
            }
        }

        return true;
    }

    @Override
    public String getWiredData()
    {
        return this.teamColor.type + "";
    }

    @Override
    public void loadWiredData(ResultSet set, Room room) throws SQLException
    {
        String data = set.getString("wired_data");

        try
        {
            if (!data.equals(""))
                this.teamColor = GameTeamColors.values()[Integer.valueOf(data)];
        }
        catch (Exception e)
        {
            this.teamColor = GameTeamColors.RED;
        }
    }

    @Override
    public void onPickUp()
    {
        this.teamColor = GameTeamColors.RED;
    }

    @Override
    public WiredConditionType getType()
    {
        return type;
    }

    @Override
    public void serializeWiredData(ServerMessage message)
    {
        message.appendBoolean(false);
        message.appendInt32(5);
        message.appendInt32(0);
        message.appendInt32(this.getBaseItem().getSpriteId());
        message.appendInt32(this.getId());
        message.appendString("");
        message.appendInt32(1);
        message.appendInt32(this.teamColor.type);
        message.appendInt32(0);
        message.appendInt32(this.getType().code);
        message.appendInt32(0);
        message.appendInt32(0);
    }

    @Override
    public boolean saveData(ClientMessage packet)
    {
        packet.readInt();

        this.teamColor = GameTeamColors.values()[packet.readInt()];

        return true;
    }
}
