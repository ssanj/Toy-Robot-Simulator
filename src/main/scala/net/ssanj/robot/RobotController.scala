package net.ssanj.robot

final case class Result(value: String)

/** Main entry point to the API.
  * Call the '''execute''' method with a [[net.ssanj.robot.Board]] and a list of
  * [[net.ssanj.robot.Command]]s to move the robot around the board.
  */
object RobotController {

  /** Executes a list of [[net.ssanj.robot.Command]]s for a given [[net.ssanj.robot.Board]]
    * against a robot and returns the output in a [[net.ssanj.robot.Result]].
    *
    *  @param board The board on which the robot will move.
    *  @param commands The commands to supply the robot.
    *  @return Result The outcome of moving the robot on the board according to the
    *  supplied commands. If a [[net.ssanj.robot.Report]] command is not supplied
    *  in the list of commands then there will be no output.
    */
  def execute(board: Board, commands: Seq[Command]): Result =
    Result(Robot.sequence(RobotNotOnBoard(board), commands).value)
}