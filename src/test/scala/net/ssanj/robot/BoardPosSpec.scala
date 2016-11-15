package net.ssanj.robot

import org.scalatest.{Matchers, WordSpecLike}

final class BoardPosSpec extends Matchers with WordSpecLike {
  "A BoardPos" should {
    "not update to a new BoardPos" when {
      "it is not on the Board" in {
        val board       = Board(Size(5, 5))
        val boardPos    = BoardPos(1, 1, North)
        val newBoardPos = boardPos.update(board, BoardPos(5, 5, East))
        newBoardPos should be (boardPos)
      }
    }

    "update to a new BoardPos" when {
      "it is on the Board" in {
        val board       = Board(Size(5, 5))
        val boardPos    = BoardPos(2, 2, South)
        val newBoardPos = boardPos.update(board, BoardPos(1, 2, West))
        newBoardPos should not (be (boardPos))
        newBoardPos should be (BoardPos(1, 2, West))
      }
    }
  }
}