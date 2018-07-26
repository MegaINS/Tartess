package ru.megains.tartess.server

import java.net.InetAddress

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.channel.{ChannelFuture, EventLoopGroup}
import ru.megains.tartess.server.network.NetworkManager
import ru.megains.tartess.server.network.protocol.TartessChannelInitializer

import scala.collection.mutable.ArrayBuffer

class NetworkSystem(server: TartessServer) {

    var networkServer: ServerBootstrap = _
    var channelFuture: ChannelFuture = _


    def startLan(address: InetAddress, port: Int): Unit = {
        val bossExec: EventLoopGroup = new NioEventLoopGroup(0)

        networkServer = new ServerBootstrap()
                .group(bossExec)
                .localAddress(address, port)
                .channel(classOf[NioServerSocketChannel])
                .childHandler(new TartessChannelInitializer(server))
        channelFuture = networkServer.bind.syncUninterruptibly()
       // channelFuture.channel().closeFuture().syncUninterruptibly()

    }

    def networkTick():Unit = {

        NetworkSystem.networkManagers = NetworkSystem.networkManagers.flatMap(
            networkManager => {

                if (!networkManager.hasNoChannel) {
                    if (networkManager.isChannelOpen) {

                        try {
                            networkManager.processReceivedPackets()
                        } catch {
                            case exception: Exception =>
                                exception.printStackTrace()
                        }
                        Some(networkManager)
                    } else {
                        networkManager.checkDisconnected()
                        None
                    }
                } else {
                    Some(networkManager)
                }

            }

        )

    }


}

object NetworkSystem {
    var networkManagers: ArrayBuffer[NetworkManager] = new ArrayBuffer[NetworkManager]
}
