package net.ssanj.robot

//TODO: Test
final case class BoardPos(x: Int, y: Int, direction: Direction) {
  def incX: BoardPos  = this.copy(x = x + 1)
  def decX: BoardPos  = this.copy(x = x - 1)
  def incY: BoardPos  = this.copy(y = y + 1)
  def decY: BoardPos  = this.copy(y = y - 1)

  def update(board: Board, newBp: BoardPos): BoardPos = {
    if (board.contains(newBp)) newBp
    else this
  }

  def left: BoardPos  = this.copy(direction = direction.left)
  def right: BoardPos = this.copy(direction = direction.right)
}
