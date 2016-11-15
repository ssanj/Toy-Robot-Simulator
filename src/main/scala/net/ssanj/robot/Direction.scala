package net.ssanj.robot

/** ADT for directions. Comprises of:
  *  1. [[net.ssanj.robot.North]]
  *  1. [[net.ssanj.robot.South]]
  *  1. [[net.ssanj.robot.East]]
  *  1. [[net.ssanj.robot.West]]
  */
sealed trait Direction extends Product with Serializable {
  val left: Direction
  val right: Direction
  val name: String
}

case object North extends Direction {
  val left  = West
  val right = East
  val name  = "North"
}

case object South extends Direction {
  val left  = East
  val right = West
  val name  = "South"
}

case object East  extends Direction {
  val left  = North
  val right = South
  val name  = "East"
}

case object West  extends Direction {
  val left  = South
  val right = North
  val name  = "West"
}

object Direction {

  val directions = Seq(North, South, East, West)

  /** Returns an optional [[net.ssanj.robot.Direction]] given a name.
    *
    *  @param name The name of the direction to return.
    *  @return Option[Direction] Some if a case-insensitive match was found or None otherwise.
    */
  def getDirection(name: String): Option[Direction] = {
    directions.find(_.name.equalsIgnoreCase(name))
  }
}
