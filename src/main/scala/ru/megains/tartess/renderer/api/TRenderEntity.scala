package ru.megains.tartess.renderer.api

import ru.megains.tartess.entity.Entity
import ru.megains.tartess.world.World

trait TRenderEntity {

    def init():Unit

    def render(entity: Entity, world: World): Boolean
}
