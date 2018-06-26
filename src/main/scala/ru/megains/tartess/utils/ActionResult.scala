package ru.megains.tartess.utils

import ru.megains.tartess.utils.EnumActionResult.EnumActionResult


class ActionResult[T](val `type`: EnumActionResult, val result: T) {

}
