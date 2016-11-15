package net.ssanj.robot

import org.scalatest.{Matchers, WordSpecLike}

final class RobotSpec extends Matchers with WordSpecLike {

  val board    = Board(Size(5, 5))
  val robot    = RobotNotOnBoard(board)

  "A Robot" should {
    "be initialised" when {
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
        val outcome = Robot.sequence(robot, commands)
        outcome.robot should be (robot)
      }
    }
  }

  it should {
    "drop Place commands" when {
      "they are not on the board" in {
        val commands =
          Seq(
            Place(BoardPos(10, 10, North)),
            Place(BoardPos(-1, 3, North)),
            Place(BoardPos(3, -1, North)),
            Place(BoardPos(4, 10, North)),
            Place(BoardPos(10, 4, North))
          )
        val outcome = Robot.sequence(robot, commands)
        outcome.robot should be (robot)
      }
    }
  }

  it should {
    "drop Place commands" when {
      "they are not on the board after initialisation" in {
        val startPos = BoardPos(1, 1, North)
        val commands =
          Seq(
            Place(startPos),
            Place(BoardPos(-1, 3, North))
          )
        val outcome = Robot.sequence(robot, commands)
        outcome.robot should be (RobotOnBoard(board , startPos))
      }
    }
  }

  it should {
    "report" when {
      "it gets a Report command" in {
        val newPos   = BoardPos(4, 4, East)
        val commands =Seq(Place(newPos), Report)
        val outcome  = Robot.sequence(robot, commands)
        outcome.robot should be (RobotOnBoard(board , newPos))
        outcome.reports should be (Seq(newPos))
      }
    }
  }

  it should {
    "move one unit in the current direction" when {
      "it gets a Move command" in {
        val startPos = BoardPos(0, 0, East)
        val newPos   = BoardPos(1, 0, East)

        val commands =Seq(Place(startPos), Move)
        val outcome  = Robot.sequence(robot, commands)
        outcome.robot should be (RobotOnBoard(board , newPos))
      }
    }
  }

  it should {
    "turn 90 degrees to the left" when {
      "given a Left command" in {
        val startPos = BoardPos(0, 0, North)
        val pos2 = BoardPos(0, 0, West)
        val pos3 = BoardPos(0, 0, South)
        val pos4 = BoardPos(0, 0, East)

        val commands = Seq(Place(startPos))
        val outcome  = Robot.sequence(robot, commands)
        outcome.robot should be (RobotOnBoard(board , startPos))
        val robotOnBoard = outcome.robot

        val outcome2 = Robot.sequence(robotOnBoard, Seq(Left))
        outcome2.robot should be (RobotOnBoard(board , pos2))

        val outcome3 = Robot.sequence(robotOnBoard, Seq(Left, Left))
        outcome3.robot should be (RobotOnBoard(board , pos3))

        val outcome4 = Robot.sequence(robotOnBoard, Seq(Left, Left, Left))
        outcome4.robot should be (RobotOnBoard(board , pos4))

        val outcome5 = Robot.sequence(robotOnBoard, Seq(Left, Left, Left, Left))
        outcome5.robot should be (RobotOnBoard(board , startPos))
      }
    }
  }

  it should {
    "turn 90 degrees to the right" when {
      "given a Right command" in {
        val startPos = BoardPos(0, 0, North)
        val pos2 = BoardPos(0, 0, East)
        val pos3 = BoardPos(0, 0, South)
        val pos4 = BoardPos(0, 0, West)

        val commands =Seq(Place(startPos))
        val outcome  = Robot.sequence(robot, commands)
        outcome.robot should be (RobotOnBoard(board , startPos))
        val robotOnBoard = outcome.robot

        val outcome2 = Robot.sequence(robotOnBoard, Seq(Right))
        outcome2.robot should be (RobotOnBoard(board , pos2))

        val outcome3 = Robot.sequence(robotOnBoard, Seq(Right, Right))
        outcome3.robot should be (RobotOnBoard(board , pos3))

        val outcome4 = Robot.sequence(robotOnBoard, Seq(Right, Right, Right))
        outcome4.robot should be (RobotOnBoard(board , pos4))

        val outcome5 = Robot.sequence(robotOnBoard, Seq(Right, Right, Right, Right))
        outcome5.robot should be (RobotOnBoard(board , startPos))
      }
    }
  }

  it should {
    "accept multiple Place commands" when {
      "it has been successfully initialised on the board" in {
        val startPos1  = BoardPos(3, 3, North)
        val endPos1    = BoardPos(2, 4, West)
        val commands1  = Seq(Place(startPos1), Move, Left, Move)
        val outcome    = Robot.sequence(robot, commands1)
        val robotAtOne = outcome.robot
        robotAtOne should be (RobotOnBoard(board, endPos1))

        val startPos2  = BoardPos(1, 2, West)
        val endPos2    = BoardPos(0, 4, North)
        val commands2  = Seq(Place(startPos2), Right, Move, Move, Left, Move, Right)
        val outcome2   = Robot.sequence(robotAtOne, commands2)
        val robotAtTwo = outcome2.robot

        robotAtTwo should be (RobotOnBoard(board , endPos2))

        val startPos3 = BoardPos(5, 5, South)
        val outcome3 = Robot.sequence(robotAtTwo, Seq(Place(startPos3)))
        //should still be at previous location, because we supplied an invalid Place.
        outcome3.robot should be (RobotOnBoard(board , endPos2))
      }
    }
  }

  it should {
    "return the supplied Robot" when {
      "Move is called in an uninitialised state" in {
        val outcome = Robot.moveByOne(robot)
        outcome.robot should be (robot)
      }
    }
  }
}

