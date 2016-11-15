package net.ssanj.robot

import org.scalatest.{Matchers, WordSpecLike}
import Outcome.printReport

final class RobotGivenExampleSpec extends Matchers with WordSpecLike {

  val board    = Board(Size(5, 5))
  val robot    = RobotNotOnBoard(board)

  "A Robot" should {
    "pass Example a" in {
      val pos      = BoardPos(0, 0, North)
      val commands = Seq(Place(pos), Move, Report)
      val outcome  = RobotController.execute(robot, commands)
      outcome.robot should be (RobotOnBoard(board, BoardPos(0, 1, North)))
      printReport(outcome.reports) should be ("0,1,NORTH")
    }

    "pass Example b" in {
      val pos      = BoardPos(0, 0, North)
      val commands = Seq(Place(pos), Left, Report)
      val outcome  = RobotController.execute(robot, commands)
      outcome.robot should be (RobotOnBoard(board, BoardPos(0, 0, West)))
      printReport(outcome.reports) should be ("0,0,WEST")
    }

    "pass Example c" in {
      val pos      = BoardPos(1, 2, East)
      val commands = Seq(Place(pos), Move, Move, Left, Move, Report)
      val outcome  = RobotController.execute(robot, commands)
      outcome.robot should be (RobotOnBoard(board, BoardPos(3, 3, North)))
      printReport(outcome.reports) should be ("3,3,NORTH")
    }
  }
}
