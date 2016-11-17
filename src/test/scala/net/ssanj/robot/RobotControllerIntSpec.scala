package net.ssanj.robot

import org.scalatest.{Matchers, WordSpecLike}
import io.IO.loadCommandsFromClasspath

final class RobotControllerIntSpec extends Matchers with WordSpecLike {
  "A RobotController" should {
    "execute commands on a Robot" when {
      "commands are loaded from a file" in {
        val commands = loadCommandsFromClasspath("commands3.txt")
        val board    = Board(Size(5, 5))
        val output   = RobotController.execute(board, commands)

        output.value should be ("2,2,NORTH")
      }
    }

    "execute commands on a Robot" when {
      "commands are loaded from a file with errors" in {
        val commands = loadCommandsFromClasspath("commands4.txt")
        val board    = Board(Size(5, 5))
        val output   = RobotController.execute(board, commands)

        output.value should be ("3,2,WEST")
      }
    }
  }
}