package net.ssanj.robot

/** Encapsulates the outcome of a command issued to a robot.
  *
  * @param robot The robot that was issued commands.
  * @param reports The output of running [[net.ssanj.robot.Report]] commands.
  */
final case class Outcome(robot: Robot, reports: Seq[BoardPos] = Seq.empty[BoardPos]) {
  val value: String = Outcome.printReport(reports)
}

object Outcome {

  private def report(bp: BoardPos): String = {
    val direction = bp.direction.name.map(_.toUpper)
    s"${bp.x},${bp.y},${direction}"
  }

  /** Provides a String representation of all [[net.ssanj.robot.Report]] commands issued.
    *
    * @param bps The list of [[net.ssanj.robot.BoardPos]] where [[net.ssanj.robot.Report]]
    * commands were issued.
    * @return String The String representation of the the Report command.
    */
  def printReport(bps: Seq[BoardPos]): String = {
    if (bps.isEmpty) "-"
    else bps.map(report).mkString("\n")
  }
}
