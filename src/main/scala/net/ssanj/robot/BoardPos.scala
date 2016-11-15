package net.ssanj.robot

/** Models a position of a robot on a [[net.ssanj.robot.Board]].
  *
  * @param x The x location of the robot.
  * @param y The y location of the robot.
  * @param direction The direction the robot is facing.
  */
final case class BoardPos(x: Int, y: Int, direction: Direction) {

  /** increments the x-location by 1 */
  def incX: BoardPos  = this.copy(x = x + 1)

  /** decrements the x-location by 1 */
  def decX: BoardPos  = this.copy(x = x - 1)

  /** increments the y-location by 1 */
  def incY: BoardPos  = this.copy(y = y + 1)

  /** decrements the y-location by 1 */
  def decY: BoardPos  = this.copy(y = y - 1)

  /** Returns the newBp if the supplied board contains it or the current
    * current BoardPos if not.
    *
    *  @param board The board on which the positions exist.
    *  @param newBp The updated board position to move to.
    *  @return BoardPos The newBp if it is value or the current BoardPos otherwise.
    */
  def update(board: Board, newBp: BoardPos): BoardPos = {
    if (board.contains(newBp)) newBp
    else this
  }

  /** The BoardPos to the left of the current one. This only changes direction not location. */
  def left: BoardPos  = this.copy(direction = direction.left)

  /** The BoardPos to the right of the current one.  This only changes direction not location. */
  def right: BoardPos = this.copy(direction = direction.right)
}
