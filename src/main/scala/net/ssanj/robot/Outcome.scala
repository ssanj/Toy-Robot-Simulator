package net.ssanj.robot

final case class Outcome(robot: Robot, reports: Seq[BoardPos] = Seq.empty[BoardPos]) {
  val value: String = Outcome.printReport(reports)
}

object Outcome {

  private def report(bp: BoardPos): String = {
    val direction = bp.direction.name.map(_.toUpper)
    s"${bp.x},${bp.y},${direction}"
  }

  //TODO: Test
  def printReport(bps: Seq[BoardPos]): String = bps.map(report).mkString("\n")
}
