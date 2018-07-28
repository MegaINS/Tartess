package ru.megains.tartess.common.network.packet.status.server

import ru.megains.tartess.common.network.handler.INetHandlerStatusClient
import ru.megains.tartess.common.network.packet.{Packet, PacketBuffer}
import ru.megains.tartess.server.ServerStatusResponse


class SPacketServerInfo() extends Packet[INetHandlerStatusClient] {
    private var response: ServerStatusResponse = null

    def this(responseIn: ServerStatusResponse) {
        this()
        this.response = responseIn
    }


    def readPacketData(buf: PacketBuffer) {
        //  this.response = JsonUtils.gsonDeserialize(SPacketServerInfo.GSON, buf.readStringFromBuffer(32767), classOf[ServerStatusResponse]).asInstanceOf[ServerStatusResponse]
    }


    def writePacketData(buf: PacketBuffer) {
        //  buf.writeString(SPacketServerInfo.GSON.toJson(this.response.asInstanceOf[Any]))
    }


    def processPacket(handler: INetHandlerStatusClient) {
        handler.handleServerInfo(this)
    }

    // def getResponse: ServerStatusResponse = this.response
}

object SPacketServerInfo {
    //  val GSON: Gson = (new GsonBuilder).registerTypeAdapter(classOf[ServerStatusResponse.Version], new ServerStatusResponse.Version#Serializer).registerTypeAdapter(classOf[ServerStatusResponse.Players], new ServerStatusResponse.Players#Serializer).registerTypeAdapter(classOf[ServerStatusResponse], new ServerStatusResponse.Serializer).registerTypeHierarchyAdapter(classOf[ITextComponent], new ITextComponent.Serializer).registerTypeHierarchyAdapter(classOf[Style], new Style.Serializer).registerTypeAdapterFactory(new EnumTypeAdapterFactory).create
}