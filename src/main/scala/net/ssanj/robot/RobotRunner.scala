package net.ssanj.robot

import io.IO.loadCommandsFromClasspath

/** Main Application */
object RobotRunner extends App {
  val board    = Board(Size(5, 5))
  val commands = loadCommandsFromClasspath("commands.txt")
  val output   = RobotController.execute(board, commands)

  println(output.value) //3,3,NORTH
}