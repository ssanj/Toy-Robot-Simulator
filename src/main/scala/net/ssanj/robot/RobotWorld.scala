package net.ssanj.robot

sealed trait Direction
case object North extends Direction
case object South extends Direction
case object East  extends Direction
case object West  extends Direction


final case class BoardPos(x: Int, y: Int, direction: Direction)

sealed trait Command
final case class Place(pos: BoardPos) extends Command
case object Move                      extends Command
case object Left                      extends Command
case object Right                     extends Command
case object Report                    extends Command

object Robot {

  def instruct(robot: Robot, command: Command): Outcome = (command, robot) match {
    case (Place(boardPos), RobotNotOnBoard(board)) =>
      if (board.contains(boardPos)) Outcome(RobotOnBoard(board, boardPos))
      else Outcome(robot)
    case (_, RobotNotOnBoard(_)) => Outcome(robot)
    case (c, RobotOnBoard(board, pos)) => ???
  }

  def validate(robot: Robot, command: Command): Boolean = ???

  def validateBoardCommand(board: Board, command: Command): Boolean = ???

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

object RobotWorld {
  //Will be used like: Seq[Intput] => Seq[Command]
  def intepret(input: String): Option[Command] = ???

  //Where should this method live?
  def exercise(robot: Robot, commands: Seq[Command]): Outcome =
    commands.foldLeft(Outcome(robot)){(o, c) => Robot.instruct(o.robot, c)}
}