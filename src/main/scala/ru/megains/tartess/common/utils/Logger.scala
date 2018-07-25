package ru.megains.tartess.common.utils

import org.apache.logging.log4j
import org.apache.logging.log4j.LogManager

trait Logger[T] {

    val log: log4j.Logger = LogManager.getLogger(asInstanceOf[T])


}
