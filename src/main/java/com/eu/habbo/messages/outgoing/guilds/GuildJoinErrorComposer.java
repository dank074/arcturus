package com.eu.habbo.messages.outgoing.guilds;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

/**
 * Created on 23-11-2014 14:49.
 */
public class GuildJoinErrorComposer extends MessageComposer
{
    public static final int GROUP_FULL = 0;
    public static final int GROUP_LIMIT_EXCEED = 1;
    public static final int GROUP_CLOSED = 2;
    public static final int GROUP_NOT_ACCEPT_REQUESTS = 3;
    public static final int NON_HC_LIMIT_REACHED = 4;
    public static final int MEMBER_FAIL_JOIN_LIMIT_EXCEED_NON_HC = 5;
    public static final int MEMBER_FAIL_JOIN_LIMIT_EXCEED_HC = 6;

    private final int code;

    public GuildJoinErrorComposer(int code)
    {
        this.code = code;
    }
    @Override
    public ServerMessage compose()
    {
        this.response.init(Outgoing.GuildJoinErrorComposer);
        this.response.appendInt32(code);
        return this.response;
    }
}
