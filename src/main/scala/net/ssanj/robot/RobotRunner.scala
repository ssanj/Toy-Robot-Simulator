package net.ssanj.robot

import io.IO.loadCommandsFromClasspath

object RobotRunner extends App {
  val board    = Board(Size(5, 5))
  val commands = loadCommandsFromClasspath("commands.txt")
  val output   = RobotController.execute(board, commands)

  println(output.string)
}