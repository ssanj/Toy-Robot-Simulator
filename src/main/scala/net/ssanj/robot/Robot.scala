package net.ssanj.robot

object Robot {

  /** Runs a single [[net.ssanj.robot.Command]] against the robot and returns the result
    * in an [[net.ssanj.robot.Outcome]].
    *
    *  @param robot The robot to issue the commands to.
    *  @param command The command to issue to the robot.
    *  @return Outcome The outcome of running the command against the robot.
    */
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

  /** Runs a sequence of commands against a robot and returns a single result in an
    * [[net.ssanj.robot.Outcome]].
    *
    *  @param robot The robot to issue the commands to.
    *  @param commands The sequence of commands to issue to the robot.
    *  @return Outcome The outcome of running the commands against the robot.
    */
  def sequence(robot: Robot, commands: Seq[Command]): Outcome =
    commands.foldLeft(Outcome(robot)){(o, c) => o ++ Robot.instruct(o.robot, c)}
  //for testing
  protected[robot] def moveByOne(robot: Robot): Outcome = robot match {
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

/** ADT for a robot which has a couple of values:
  *  1. RobotNotOnBoard - Models a robot that is not on the board.
  *  1. RobotOnBoard - Models a robot that is on the board.
  *
  */
sealed trait Robot {
  val board: Board
}

/** Models a robot that is not on the board. This is the case when the robot has
  * not received a (valid) [[net.ssanj.robot.Place]] command.
  *
  *  @param board The board on which to move.
  */
final case class RobotNotOnBoard (board: Board) extends Robot

/** Models a robot that is on the board. This implies that the robot has received one
  * or more valid [[net.ssanj.robot.Place]] and possibly other commands.
  *
  *  @param board The board on which to move.
  *  @param pos The position on the board occupied by the robot.
  */
final case class RobotOnBoard(board: Board, pos: BoardPos) extends Robot