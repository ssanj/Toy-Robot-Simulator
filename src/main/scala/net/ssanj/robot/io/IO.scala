package net.ssanj.robot.io

import scala.util.Try

import net.ssanj.robot.{Command, CommandParser}

object IO {

  def loadCommandsFromClasspath(file: String): Seq[Command] = Try {
    val in     = getClass.getClassLoader.getResourceAsStream(file)
    val buffer = scala.io.Source.fromInputStream(in)
    buffer.getLines.toSeq.flatMap(CommandParser.interpret(_).toList)
  }.toOption.toList.flatten
}
