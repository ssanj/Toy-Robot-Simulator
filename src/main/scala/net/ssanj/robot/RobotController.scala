package net.ssanj.robot

//TODO: Rename this to RobotController
object RobotController {

  def execute(robot: Robot, commands: Seq[Command]): Outcome =
    commands.foldLeft(Outcome(robot)){(o, c) => Robot.instruct(o.robot, c)}

  def execute(board: Board, commands: Seq[Command]): Outcome =
    commands.foldLeft(Outcome(RobotNotOnBoard(board))){(o, c) => Robot.instruct(o.robot, c)}
}