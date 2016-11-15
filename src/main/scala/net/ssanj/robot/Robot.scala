package net.ssanj.robot

object Robot {

  def instruct(robot: Robot, command: Command): Outcome = (command, robot) match {
    case (Place(boardPos), RobotNotOnBoard(board)) if board.contains(boardPos) => Outcome(RobotOnBoard(board, boardPos))
    case (_, RobotNotOnBoard(_))              => Outcome(robot)
    case (Report, RobotOnBoard(_, pos))       => Outcome(robot, Seq(pos))
    case (Move, r @ RobotOnBoard(_, _))       => moveByOne(r)
    case (Left, r @ RobotOnBoard(_, bp))      => Outcome(r.copy(pos = bp.left))
    case (Right, r @ RobotOnBoard(_, bp))     => Outcome(r.copy(pos = bp.right))
    case (Place(pos), r @ RobotOnBoard(board, _)) if board.contains(pos) => Outcome(r.copy(pos = pos))
    case (_, r @ RobotOnBoard(_, _))          => Outcome(r)
  }

  def sequence(robot: Robot, commands: Seq[Command]): Outcome =
    commands.foldLeft(Outcome(robot)){(o, c) => Robot.instruct(o.robot, c)}

  private def moveByOne(robot: Robot): Outcome = robot match {
    case r @ RobotOnBoard(board, bp @ BoardPos(_, _, North)) =>
        Outcome(r.copy(pos = bp.update(board, bp.incY)))
    case r @ RobotOnBoard(board, bp @ BoardPos(_, _, South)) =>
        Outcome(r.copy(pos = bp.update(board, bp.decY)))
    case r @ RobotOnBoard(board, bp @ BoardPos(_, _, East)) =>
        Outcome(r.copy(pos = bp.update(board, bp.incX)))
    case r @ RobotOnBoard(board, bp @ BoardPos(_, _, West)) =>
        Outcome(r.copy(pos = bp.update(board, bp.decX)))
    case _ => Outcome(robot)
  }
}

sealed trait Robot {
  val board: Board
}

final case class RobotNotOnBoard (board: Board) extends Robot

final case class RobotOnBoard(board: Board, pos: BoardPos) extends Robot