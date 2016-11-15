package net.ssanj.robot.io

import scala.util.Try
import net.ssanj.robot.{RobotWorld, Command}

object IO {

  def loadCommandsFromClasspath(file: String): Seq[Command] = Try {
    val  buffer = scala.io.Source.fromInputStream(getClass.getClassLoader.getResourceAsStream(file))
    buffer.getLines.map(RobotWorld.interpret(_)).flatten.toSeq
  }.toOption.toSeq.flatten
}