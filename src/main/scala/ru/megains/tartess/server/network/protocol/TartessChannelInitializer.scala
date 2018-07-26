package ru.megains.tartess.server.network.protocol

import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.channel.{ChannelInitializer, ChannelOption}
import ru.megains.tartess.common.network.protocol.TartessCodec
import ru.megains.tartess.server.TartessServer
import ru.megains.tartess.server.network.{NetHandlerHandshakeTCP, NetworkManager}

class TartessChannelInitializer(server:TartessServer) extends ChannelInitializer[NioSocketChannel]{


    override def initChannel(ch: NioSocketChannel): Unit = {
        val networkManager = new NetworkManager(server)
        ch.pipeline()
                .addLast("serverCodec",new TartessCodec)
                .addLast("messageCodec",new TartessMessageCodec)
                .addLast("packetHandler", networkManager)
        ch.config.setOption(ChannelOption.TCP_NODELAY, Boolean.box(true))


       // NetworkSystem.networkManagers :+= networkManager

        networkManager.setNetHandler(new NetHandlerHandshakeTCP(server, networkManager))
    }
}