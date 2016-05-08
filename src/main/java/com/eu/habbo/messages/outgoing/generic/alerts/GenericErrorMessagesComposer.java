package com.eu.habbo.messages.outgoing.generic.alerts;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

/**
 * Created on 26-1-2015 11:47.
 */
public class GenericErrorMessagesComposer extends MessageComposer
{
    public static final int AUTHENTICATION_FAILED = -3;
    public static final int CONNECTING_TO_THE_SERVER_FAILED = -400;
    public static final int KICKED_OUT_OF_THE_ROOM = 4008;
    public static final int NEED_TO_BE_VIP = 4009;
    public static final int ROOM_NAME_UNACCEPTABLE = 4010;
    public static final int CANNOT_BAN_GROUP_MEMBER = 4011;

    private final int errorCode;

    public GenericErrorMessagesComposer(int errorCode)
    {
        this.errorCode = errorCode;
    }
    @Override
    public ServerMessage compose()
    {
        this.response.init(Outgoing.GenericErrorMessages);
        this.response.appendInt32(this.errorCode);
        return this.response;
    }
}
