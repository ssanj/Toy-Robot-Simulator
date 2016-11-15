package net.ssanj.robot

import org.scalacheck.Properties
import org.scalacheck.{Prop, Gen}

object RobotWorldProps extends Properties("RobotWorld") {

  final case class InputCommand(value: String, expected: Option[Command])

  final case class InputCommands(value: Seq[String], expected: Seq[Command])

  private def genPlaceCommand: Gen[InputCommand] = for {
    size <- Gen.choose(1, 10)
    x    <- Gen.choose(1, size)
    y    <- Gen.choose(1, size)
    dir  <- Gen.oneOf("NORTH", "SOUTH", "EAST", "WEST")
  } yield
    InputCommand(
      value    = s"PLACE ${x},${y},${dir}",
      expected = Option(Place(BoardPos(x, y, Direction.getDirection(dir).get))))

  private def getInputCommands: Gen[InputCommands] = for {
    n        <- Gen.choose(1, 10)
    invalid  <- Gen.listOfN(n, Gen.alphaStr)
    place    <- genPlaceCommand
    commands <- Gen.listOfN(n, Gen.oneOf("MOVE", "LEFT", "RIGHT", "REPORT"))
  } yield InputCommands(
    value    = (invalid :+ place.value) ++ invalid ++ commands ++ invalid,
    expected =
      CommandParser.interpret(place.value).get +: commands.map(CommandParser.interpret(_).get))

  property("interpret PLACE commands") =
    Prop.forAll(genPlaceCommand) { input =>
      CommandParser.interpret(input.value) == input.expected
    }

  property("getCommands") =
    Prop.forAll(getInputCommands) { inputs =>
      CommandParser.getCommands(inputs.value) == inputs.expected
    }
}