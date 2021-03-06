package ru.megains.tartess.client.network.protocol

import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.channel.{ChannelInitializer, ChannelOption}
import ru.megains.tartess.common.network.NetworkManager
import ru.megains.tartess.common.network.protocol.{TartessCodec, TartessMessageCodec}


class TartessChannelInitializer(networkManager:NetworkManager) extends ChannelInitializer[NioSocketChannel]{


    override def initChannel(ch: NioSocketChannel): Unit = {
        ch.pipeline()
                .addLast("serverCodec",new TartessCodec)
                .addLast("messageCodec", new TartessMessageCodec)
                .addLast("packetHandler", networkManager)
        ch.config.setOption(ChannelOption.TCP_NODELAY, Boolean.box(true))
    }
}
