package net.ssanj.robot

/** Models the size of a board.
  *
  *  @param length The length of the board.
  *  @param width The width of the board.
  */
final case class Size(length: Int, width: Int)

/** Models a board on which the robot moves.
  *
  *  @param size The size of the board.
  *  @see [[net.ssanj.robot.Size]]
  */
final case class Board(size: Size) {

  /** Returns true if this board contains the supplied [[net.ssanj.robot.BoardPos]]
    *
    *  @param boardPos The position of the robot on the board.
    */
  def contains(boardPos: BoardPos): Boolean = {
    (boardPos.x >= 0 && boardPos.x < size.length) &&
    (boardPos.y >= 0 && boardPos.y < size.width)
  }
}