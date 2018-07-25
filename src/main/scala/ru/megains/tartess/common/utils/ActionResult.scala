package ru.megains.tartess.common.utils

import ru.megains.tartess.common.utils.EnumActionResult.EnumActionResult


class ActionResult[T](val `type`: EnumActionResult, val result: T) {

}
