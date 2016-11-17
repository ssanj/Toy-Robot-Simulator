package net.ssanj.robot

/** Encapsulates the outcome of a command issued to a robot.
  *
  * @param robot The robot that was issued commands.
  * @param reports The output of running [[net.ssanj.robot.Report]] commands.
  */
final case class Outcome(robot: Robot, reports: Seq[BoardPos] = Seq.empty[BoardPos]) {

  //String-representation of reports.
  val value: String = Outcome.printReport(reports)

  /** This method essentially maintains the history of reports across multiple Outcomes by
    * combining two Outcomes to produce a third. The rules for combination are as follows:
    *  1. Use the other Outcome's robot. (The later Outcome wins in terms of position)
    *  1. Prepend this Outcome's reports to that of the other. (Maintains chronological reporting)
    *
    *  @param other An Outcome that occurs after this one.
    */
  def ++(other: Outcome): Outcome = other.copy(reports = reports ++ other.reports)
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
