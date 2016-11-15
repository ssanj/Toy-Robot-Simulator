package net.ssanj.robot

import org.scalacheck.{Gen, Prop, Properties}

object CommandParserProps extends Properties("CommandParser") {

  final case class InputCommand(value: String, expected: Option[Command])

  final case class InputCommands(value: Seq[String], expected: Seq[Command])

  private val validInputs = Seq("MOVE", "LEFT", "RIGHT", "REPORT")

  private def genPlaceCommand: Gen[InputCommand] = for {
    size <- Gen.choose(1, 10)
    x    <- Gen.choose(1, size)
    y    <- Gen.choose(1, size)
    dir  <- Gen.oneOf("NORTH", "SOUTH", "EAST", "WEST")
  } yield
    InputCommand(
      value    = s"PLACE ${x},${y},${dir}",
      expected = Option(Place(BoardPos(x, y, Direction.getDirection(dir).get))))

  private def getInvalidInputs(valid: Seq[String]): Gen[Seq[String]] = for {
    n       <- Gen.choose(1, 5)
    invalid <- Gen.listOfN(n, Gen.alphaStr).filter(x => !valid.contains(x))
  } yield invalid

  private def getInputCommands: Gen[InputCommands] = for {
    invalid1  <- getInvalidInputs(validInputs)
    invalid2  <- getInvalidInputs(validInputs)
    invalid3  <- getInvalidInputs(validInputs)
    n       <- Gen.choose(1, 5)
    place    <- genPlaceCommand
    commands <- Gen.listOfN(n, Gen.oneOf(validInputs))
  } yield InputCommands(
    value    = (invalid1 :+ place.value) ++ invalid2 ++ commands ++ invalid3,
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
