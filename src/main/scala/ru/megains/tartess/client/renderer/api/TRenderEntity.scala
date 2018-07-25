package ru.megains.tartess.client.renderer.api

import ru.megains.tartess.common.entity.Entity
import ru.megains.tartess.common.world.World

trait TRenderEntity {

    def init():Unit

    def render(entity: Entity, world: World): Boolean
}
