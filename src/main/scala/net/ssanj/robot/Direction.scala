package net.ssanj.robot

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

  def getDirection(value: String): Option[Direction] = {
    directions.find(_.name.equalsIgnoreCase(value))
  }
}
