package net.ssanj.robot

import org.scalacheck.{Gen, Prop, Properties}

object BoardProps extends Properties("Board") {

  private def genValidBoardPos: Gen[(Board, BoardPos)] = for {
    size <- Gen.choose(3, 10)
    x    <- Gen.choose(0, Math.max(0, size - 1))
    y    <- Gen.choose(0, Math.max(0, size - 1))
    dir  <- Gen.oneOf(North, South, East, West)
  } yield (Board(Size(size, size)), BoardPos(x, y, dir))

  property("contains") =
    Prop.forAll(genValidBoardPos) {
      case (board, boardPos) => board.contains(boardPos)
    }
  }
