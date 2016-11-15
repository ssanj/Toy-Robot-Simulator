package net.ssanj.robot

final case class Result(value: String)

//TODO: Rename this to RobotController
object RobotController {

  //This should not return Outcome
  def execute(board: Board, commands: Seq[Command]): Result =
    Result(Robot.instruct(RobotNotOnBoard(board), commands).string)
}