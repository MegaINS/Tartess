package ru.megains.tartess.server.world

import ru.megains.tartess.common.world.World
import ru.megains.tartess.common.world.data.AnvilSaveHandler
import ru.megains.tartess.server.TartessServer

class WorldServer(val server:TartessServer, saveHandler:AnvilSaveHandler) extends World(saveHandler){

}
