package net.ssanj.robot

sealed trait Direction
case object North extends Direction
case object South extends Direction
case object East  extends Direction
case object West  extends Direction

object Direction {

  //TODO: Test
  def getDirection(value: String): Option[Direction] = value match {
    case x if x.equalsIgnoreCase("North") => Option(North)
    case x if x.equalsIgnoreCase("South") => Option(South)
    case x if x.equalsIgnoreCase("East")  => Option(East)
    case x if x.equalsIgnoreCase("West")  => Option(West)
  }
}


final case class BoardPos(x: Int, y: Int, direction: Direction) {
  def incX: BoardPos = BoardPos(x + 1, y, direction)
  def decX: BoardPos = BoardPos(x - 1, y, direction)
  def incY: BoardPos = BoardPos(x, y + 1, direction)
  def decY: BoardPos = BoardPos(x, y - 1, direction)
}

sealed trait Command
final case class Place(pos: BoardPos) extends Command
case object Move                      extends Command
case object Left                      extends Command
case object Right                     extends Command
case object Report                    extends Command

object Robot {

  def instruct(robot: Robot, command: Command): Outcome = (command, robot) match {
    case (Place(boardPos), RobotNotOnBoard(board)) if board.contains(boardPos) => Outcome(RobotOnBoard(board, boardPos))
    case (_, RobotNotOnBoard(_))              => Outcome(robot)
    case (Report, RobotOnBoard(_, pos))       => Outcome(robot, Seq(pos))
    case (Move, r @ RobotOnBoard(_, _))       => moveByOne(r)
    case (Left, r @ RobotOnBoard(_, bp))      =>  Outcome(r.copy(pos = turnLeft(bp)))
    case (Right, r @ RobotOnBoard(_, bp))     => Outcome(r.copy(pos = turnRight(bp)))
    case (Place(pos), r @ RobotOnBoard(board, _)) if board.contains(pos) => Outcome(r.copy(pos = pos))
    case (_, r @ RobotOnBoard(_, _))          => Outcome(r)
  }

  def turnLeft(boardPos: BoardPos): BoardPos = boardPos match {
    case bp @ BoardPos(_, _, North) => bp.copy(direction = West)
    case bp @ BoardPos(_, _, South) => bp.copy(direction = East)
    case bp @ BoardPos(_, _, East)  => bp.copy(direction = North)
    case bp @ BoardPos(_, _, West)  => bp.copy(direction = South)
  }

  //TODO: Can we use some look up for these values?
  //modulo List? ["N", "E", "S", "W"], Left = -1, Right +1 and rotate
  def turnRight(boardPos: BoardPos): BoardPos = boardPos match {
    case bp @ BoardPos(_, _, North) => bp.copy(direction = East)
    case bp @ BoardPos(_, _, South) => bp.copy(direction = West)
    case bp @ BoardPos(_, _, East)  => bp.copy(direction = South)
    case bp @ BoardPos(_, _, West)  => bp.copy(direction = North)
  }

  //TODO: Make Board handle the invalid cases and keep this clean.
  def moveByOne(robot: Robot): Outcome = robot match {
    case r @ RobotOnBoard(board, bp @ BoardPos(_, _, North)) if board.contains(bp.incY) =>
        Outcome(r.copy(pos = bp.incY))
    case r @ RobotOnBoard(board, bp @ BoardPos(_, _, South)) if board.contains(bp.decY) =>
        Outcome(r.copy(pos = bp.decY))
    case r @ RobotOnBoard(board, bp @ BoardPos(_, _, East)) if board.contains(bp.incX) =>
        Outcome(r.copy(pos = bp.incX))
    case r @ RobotOnBoard(board, bp @ BoardPos(_, _, West)) if board.contains(bp.decX) =>
        Outcome(r.copy(pos = bp.decX))
    case _ => Outcome(robot)
  }
}

sealed trait Robot {
  val board: Board
}

//TODO: How can we make it such that you can't create Robot without a valid board?
//What we want to do is to force the creation of RobotNotOnBoard and then
//have the code move that to RobotOnBoard when given a valid Place command.
final case class RobotNotOnBoard (board: Board) extends Robot
final case class RobotOnBoard(board: Board, pos: BoardPos) extends Robot

final case class Size(length: Int, width: Int)
final case class Board(size: Size) {
  def contains(boardPos: BoardPos): Boolean =
  (boardPos.x >= 0 && boardPos.x < size.length) &&
  (boardPos.y >= 0 && boardPos.y < size.width)
}
final case class Outcome(robot: Robot, reports: Seq[BoardPos] = Seq.empty)

object Outcome {

  private def report(bp: BoardPos): String =
  s"${bp.x},${bp.y},${bp.direction.toString.map(_.toUpper)}"

  def printReport(bps: Seq[BoardPos]): String = {
    if (bps.length == 1) report(bps.head)
    else {
      bps.map(report(_)).mkString("\n")
    }
  }
}

object RobotWorld {

  import scala.util.Try

  val PlaceCommand = """PLACE\s(\d+),(\d+),(NORTH|SOUTH|EAST|WEST)""".r

  def interpret(input: String): Option[Command] = input match {
    case PlaceCommand(x, y, d) => for {
      xPos <- Try(x.toInt).toOption
      yPos <- Try(y.toInt).toOption
      dir  <- Direction.getDirection(d)
    } yield Place(BoardPos(xPos, yPos, dir))

    case "MOVE" => Option(Move)
    case "LEFT" => Option(Left)
    case "RIGHT" => Option(Right)
    case "REPORT" => Option(Report)
    case _ => None
  }

  def getCommands(inputs: Seq[String]): Seq[Command] = inputs.map(interpret(_)).flatten

  // def validate(robot: Robot, command: Command): Boolean = ???

  // def validateBoardCommand(board: Board, command: Command): Boolean = ???

  //Where should this method live?
  def exercise(robot: Robot, commands: Seq[Command]): Outcome =
    commands.foldLeft(Outcome(robot)){(o, c) => Robot.instruct(o.robot, c)}
}