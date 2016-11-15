package net.ssanj.robot

object CommandParser {
  import scala.util.Try

  val PlaceCommand = """PLACE\s(\d+),(\d+),(NORTH|SOUTH|EAST|WEST)""".r

  def interpret(input: String): Option[Command] = input match {
    case PlaceCommand(x, y, d) => for {
      xPos <- Try(x.toInt).toOption
      yPos <- Try(y.toInt).toOption
      dir  <- Direction.getDirection(d)
    } yield Place(BoardPos(xPos, yPos, dir))

    case "MOVE" => Option(Move)
    case "LEFT" => Option(Left)
    case "RIGHT" => Option(Right)
    case "REPORT" => Option(Report)
    case _ => None
  }

  def getCommands(inputs: Seq[String]): Seq[Command] = inputs.map(interpret(_)).flatten
}