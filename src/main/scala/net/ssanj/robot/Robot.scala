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

  //TODO: Test
  def moveByOne(robot: Robot): Outcome = robot match {
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

//TODO: How can we make it such that you can't create Robot without a valid board?
//What we want to do is to force the creation of RobotNotOnBoard and then
//have the code move that to RobotOnBoard when given a valid Place command.
final case class RobotNotOnBoard (board: Board) extends Robot
final case class RobotOnBoard(board: Board, pos: BoardPos) extends Robot