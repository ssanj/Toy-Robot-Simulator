package net.ssanj.robot.io

import scala.util.Try

import net.ssanj.robot.{Command, CommandParser}

object IO {

  /** Loads a file from the classpath and parses it to [[net.ssanj.robot.Command]]s.
    * Any invalid Commands are skipped. If there are any errors loading the file an
    * empty list will be returned.
    *
    * @param file The classpath resource to load. The file should have one Command per line.
    * @return Seq[Command] The successfully parsed Commands or an empty list if not.
    */
  def loadCommandsFromClasspath(file: String): Seq[Command] = Try {
    val in     = getClass.getClassLoader.getResourceAsStream(file)
    val buffer = scala.io.Source.fromInputStream(in)
    buffer.getLines.toSeq.flatMap(CommandParser.interpret(_).toList)
  }.toOption.toList.flatten
}
