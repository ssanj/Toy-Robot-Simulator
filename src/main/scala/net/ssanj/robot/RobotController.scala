package net.ssanj.robot

final case class Result(value: String)

object RobotController {

  def execute(board: Board, commands: Seq[Command]): Result =
    Result(Robot.instruct(RobotNotOnBoard(board), commands).value)
}