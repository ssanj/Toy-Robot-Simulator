package net.ssanj.robot

import org.scalacheck.{Prop, Properties, Gen}

object OutcomeProps extends Properties("Outcome") {

  private def genValidBoardPos: Gen[BoardPos] = for {
    size <- Gen.choose(3, 10)
    x    <- Gen.choose(0, Math.max(0, size - 1))
    y    <- Gen.choose(0, Math.max(0, size - 1))
    dir  <- Gen.oneOf(North, South, East, West)
  } yield BoardPos(x, y, dir)


  private def genListOfBoardPos: Gen[Seq[BoardPos]] = for {
     n   <- Gen.choose(0, 3)
     bps <- Gen.listOfN(n, genValidBoardPos)
  } yield bps

  property("printReport") =
    Prop.forAll(genListOfBoardPos) { bps =>
      val printedOutcome = Outcome.printReport(bps)
      if (bps.isEmpty) {
         printedOutcome == "-"
      } else {
        printedOutcome ==
          bps.map { bp =>
            s"${bp.x},${bp.y},${bp.direction.name.map(_.toUpper)}"
          }.mkString("\n")
      }
    }
  }