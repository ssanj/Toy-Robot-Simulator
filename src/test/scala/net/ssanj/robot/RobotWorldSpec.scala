package net.ssanj.robot

import org.scalatest.{Matchers, WordSpecLike}

final class RobotWorldSpec extends Matchers with WordSpecLike {

  val board    = Board(Size(5, 5))
  val robot    = RobotNotOnBoard(board)


  "A RobotWorld" should {
    "initialise a Robot" when {
      "given a valid Place command" in {
        val command  = Place(BoardPos(0, 0, North))
        val outcome  = Robot.instruct(robot, command)

        outcome match {
          case Outcome(RobotOnBoard(bd, BoardPos(x, y, dir)), reports) =>
            board should be (bd)
            x should be (0)
            y should be (0)
            dir should be (North)
          case Outcome(RobotNotOnBoard(_), _) =>
            fail("Robot is not on board")
        }
      }
    }
  }

  it should {
    "drop non-Place commands" when {
      "not initialised on the board" in {
        val commands = Seq(Move, Left, Right, Report)
        val outcome = RobotWorld.exercise(robot, commands)
        outcome.robot should be (robot)
      }
    }
  }

  it should {
    "drop Place commands" when {
      "that are not on the board" in {
        val commands =
          Seq(
            Place(BoardPos(10, 10, North)),
            Place(BoardPos(-1, 3, North)),
            Place(BoardPos(3, -1, North)),
            Place(BoardPos(4, 10, North)),
            Place(BoardPos(10, 4, North)))
        val outcome = RobotWorld.exercise(robot, commands)
        outcome.robot should be (robot)
      }
    }
  }
}

