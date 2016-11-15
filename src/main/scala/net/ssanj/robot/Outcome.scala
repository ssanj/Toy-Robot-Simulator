package net.ssanj.robot

final case class Outcome(robot: Robot, reports: Seq[BoardPos] = Seq.empty) {
  val string: String = Outcome.printReport(reports)
}

object Outcome {

  private def report(bp: BoardPos): String =
  s"${bp.x},${bp.y},${bp.direction.toString.map(_.toUpper)}"

  //TODO: Test
  def printReport(bps: Seq[BoardPos]): String = {
    if (bps.length == 1) report(bps.head)
    else {
      bps.map(report(_)).mkString("\n")
    }
  }
}
