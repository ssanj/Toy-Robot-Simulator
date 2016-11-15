package net.ssanj.robot

object CommandParser {
  import scala.util.Try

  private val PlaceCommand = """PLACE\s(\d+),(\d+),(NORTH|SOUTH|EAST|WEST)""".r

  /** Parser of String to [[net.ssanj.robot.Command]]s. When the input is valid
    * returns the matching Command.
    *
    *  @param input The command as a String.
    *  @return Option[Command] A Some if the input matched a Command, None otherwise.
    */
  def interpret(input: String): Option[Command] = input match {
    case PlaceCommand(x, y, d) => for {
      xPos <- Try(x.toInt).toOption
      yPos <- Try(y.toInt).toOption
      dir  <- Direction.getDirection(d)
    } yield Place(BoardPos(xPos, yPos, dir))

    case "MOVE"   => Option(Move)
    case "LEFT"   => Option(Left)
    case "RIGHT"  => Option(Right)
    case "REPORT" => Option(Report)
    case _        => None
  }

  /** Returns a list of [[net.ssanj.robot.Command]]s given a list of input Strings.
    *
    *  @param inputs The commands as Strings.
    *  @return Seq[Command] The list of matched Commands.
    */
  def getCommands(inputs: Seq[String]): Seq[Command] = inputs.flatMap(interpret(_).toList)
}
