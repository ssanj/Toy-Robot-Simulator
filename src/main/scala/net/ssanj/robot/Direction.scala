package net.ssanj.robot

sealed trait Direction {
  val left: Direction
  val right: Direction
}

case object North extends Direction {
  val left  = West
  val right = East
}

case object South extends Direction {
  val left  = East
  val right = West
}

case object East  extends Direction {
  val left = North
  val right = South
}

case object West  extends Direction {
  val left  = South
  val right = North
}

object Direction {

  //TODO: Test
  def getDirection(value: String): Option[Direction] = value match {
    case x if x.equalsIgnoreCase("North") => Option(North)
    case x if x.equalsIgnoreCase("South") => Option(South)
    case x if x.equalsIgnoreCase("East")  => Option(East)
    case x if x.equalsIgnoreCase("West")  => Option(West)
  }
}
