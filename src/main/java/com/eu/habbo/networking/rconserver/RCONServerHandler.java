package com.eu.habbo.networking.rconserver;


import com.eu.habbo.Emulator;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created on 18-7-2015 10:07.
 */
public class RCONServerHandler extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception
    {
        String adress = ctx.channel().remoteAddress().toString().split(":")[0].replace("/", "");

        for(String s : RCONServer.allowedAdresses)
        {
            if(s.equalsIgnoreCase(adress))
            {
                return;
            }
        }

        ctx.close();
        Emulator.getLogging().logDebugLine("Remote connection closed: " + adress + ". IP not allowed!");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        ByteBuf data = (ByteBuf) msg;

        byte[] d = new byte[data.readableBytes()];
        data.getBytes(0, d);
        String message = new String(d);
        Gson gson = new Gson();
        String response = gson.toJson("ERROR", String.class);


        try
        {
            JsonObject object = gson.fromJson(message, JsonObject.class);
            String key = object.get("key").getAsString();
            response = Emulator.getRconServer().handle(ctx, key, object.get("data").toString());
        }
        catch (Exception e)
        {
            Emulator.getLogging().logDebugLine("[RCON] Not JSON: " + message);
            e.printStackTrace();
        }

        ChannelFuture f = ctx.channel().write(Unpooled.copiedBuffer(response.getBytes()), ctx.channel().voidPromise());
        ctx.channel().flush();
        ctx.flush();
        f.channel().close();

        //ctx.channel().close();
    }
}
