package net.ssanj.robot

final case class Size(length: Int, width: Int)

final case class Board(size: Size) {
  def contains(boardPos: BoardPos): Boolean =
  (boardPos.x >= 0 && boardPos.x < size.length) &&
  (boardPos.y >= 0 && boardPos.y < size.width)
}