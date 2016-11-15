package net.ssanj.robot

/** ADT for Commands. Comprises of the following:
  *  1. [[net.ssanj.robot.Place]]
  *  1. [[net.ssanj.robot.Move]]
  *  1. [[net.ssanj.robot.Left]]
  *  1. [[net.ssanj.robot.Right]]
  *  1. [[net.ssanj.robot.Report]]
  */
sealed trait Command

/** Places the robot on the board at a given x and y position facing in a [[net.ssanj.robot.Direction]].
  *
  *  @param pos The position of the robot and the direction it is facing.
  */
final case class Place(pos: BoardPos) extends Command

/** Moves the robot forward by 1 unit. */
case object Move                      extends Command

/** Turns the robot to the left by 90 degrees at the current location. */
case object Left                      extends Command

/** Turns the robot to the right by 90 degrees at the current location. */
case object Right                     extends Command

/** Returns the current location and face of the robot. */
case object Report                    extends Command
