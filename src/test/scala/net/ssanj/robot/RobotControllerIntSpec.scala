package net.ssanj.robot

import org.scalatest.{Matchers, WordSpecLike}
import io.IO.loadCommandsFromClasspath
import Outcome.printReport

final class RobotControllerIntSpec extends Matchers with WordSpecLike {
  "A RobotController" should {
    "execute Robot" when {
      "commands are loaded from a file" in {
        val commands = loadCommandsFromClasspath("commands3.txt")
        val board    = Board(Size(5, 5))
        val robot    = RobotNotOnBoard(board)
        val output   = RobotController.execute(robot, commands)

        output.robot should be (RobotOnBoard(board, BoardPos(2, 2, North)))
        printReport(output.reports) should be ("2,2,NORTH")
      }
    }
  }
}